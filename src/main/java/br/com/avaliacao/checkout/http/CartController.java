package br.com.avaliacao.checkout.http;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/cart")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CartController {
	
    @Autowired
    private ShoppingCart shoppingCart;

    @RequestMapping(value = "/adicionar", method={RequestMethod.POST})
    public ResponseEntity<?> addToCart(@RequestBody CartTO to) {
    	
    	ResponseEntity<?> response;
    	
    	try {
    		Cart cart = shoppingCart.addItem(to);
    		
			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(getLocation(cart.getCartId()));
			response = new ResponseEntity<Cart>(cart, httpHeaders, HttpStatus.CREATED);

			
		} catch (CartInvalidException exception) {
			final MessageErrorTO messageError = exception.getMessageError();
			response = new ResponseEntity<>(messageError, HttpStatus.CONFLICT);
		
		}catch (Exception e) {				
			MessageErrorTO messageError = new MessageErrorTO(Constants.ERRO_INESPERADO);
			response = new ResponseEntity<>(messageError, HttpStatus.INTERNAL_SERVER_ERROR);
		}

    	return response;
    }

	private URI getLocation(final String id) {
		final ServletUriComponentsBuilder request = ServletUriComponentsBuilder.fromCurrentRequest();
		final UriComponentsBuilder path = request.path("/{id}");
		return path.buildAndExpand(id).toUri();
	}

}

