package br.com.avaliacao.checkout.http;

import java.net.URI;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.avaliacao.checkout.constants.Constants;
import br.com.avaliacao.checkout.exception.CartInvalidException;
import br.com.avaliacao.checkout.facade.ShoppingCart;
import br.com.avaliacao.checkout.model.Cart;
import br.com.avaliacao.checkout.to.CartTO;
import br.com.avaliacao.checkout.to.MessageErrorTO;
import br.com.avaliacao.checkout.validation.CartValidation;

@RestController
@RequestMapping("/cart")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CartController {
	
	private static final Logger LOGGER = Logger.getLogger(CartController.class);
	
	@Autowired
	private MessageSource messageSource;
	
    @Autowired
    private ShoppingCart shoppingCart;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new CartValidation());
	}

    @RequestMapping(method={RequestMethod.POST}, value = "/adicionar")
    public ResponseEntity<?> addToCart(@RequestBody @Valid CartTO to, BindingResult result) {
    	
    	LOGGER.info("addToCart Inicio");
    	
    	ResponseEntity<?> response;
    	
    	if (result.hasErrors()) {    	
    		LOGGER.info("Erro na validacao dos campos");
    		
    		String error = localizedErrorMessage(result.getFieldError());
    		MessageErrorTO messageError = new MessageErrorTO(error);
			response = new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
			return response;
		}
    	
    	try {
    		Cart cart = shoppingCart.addItem(to);
    		
			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(getLocation(cart.getCartId()));
			response = new ResponseEntity<Cart>(cart, httpHeaders, HttpStatus.CREATED);

			LOGGER.info("Item adicionado no carrrinho com sucesso.");
			
		} catch (CartInvalidException exception) {
			LOGGER.info("Carrinho inválido", exception);
			
			final MessageErrorTO messageError = exception.getMessageError();
			response = new ResponseEntity<>(messageError, HttpStatus.CONFLICT);
		
		}catch (Exception exception) {
			LOGGER.info(Constants.ERRO_INESPERADO, exception);
			
			MessageErrorTO messageError = new MessageErrorTO(Constants.ERRO_INESPERADO);
			response = new ResponseEntity<>(messageError, HttpStatus.INTERNAL_SERVER_ERROR);
		}

    	return response;
    }

    @RequestMapping(method={RequestMethod.GET}, value = "/{cartId}")
    public ResponseEntity<?> findCart(@PathVariable String cartId) {
		
    	LOGGER.info("findCart Inicio");
    	
    	Cart cart = shoppingCart.findOne(cartId);
    	
    	ResponseEntity<?> response = new ResponseEntity<>(cart, HttpStatus.OK);
    	
    	if (cart == null) {
    		LOGGER.info(Constants.CART_NOT_FOUND);
			MessageErrorTO messageError = new MessageErrorTO(Constants.CART_NOT_FOUND);
			response = new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
		}
    	
    	return response;
	}
    
	private URI getLocation(final String id) {
		final ServletUriComponentsBuilder request = ServletUriComponentsBuilder.fromCurrentRequest();
		final UriComponentsBuilder path = request.path("/{id}");
		return path.buildAndExpand(id).toUri();
	}
	
    private String localizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
 
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
 
        return localizedErrorMessage;
    }

}

