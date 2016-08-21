package br.com.avaliacao.checkout.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.avaliacao.AvaliacaoJavaApplication;
import br.com.avaliacao.checkout.constants.Constants;
import br.com.avaliacao.checkout.facade.ShoppingCart;
import br.com.avaliacao.checkout.mock.to.CartTOMock;
import br.com.avaliacao.checkout.model.Cart;
import br.com.avaliacao.checkout.to.CartTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AvaliacaoJavaApplication.class)
@WebAppConfiguration
public class CartControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    
    @SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private ShoppingCart shoppingCart;
    
    private CartTO cartTO;
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.cartTO = new CartTOMock().createMock();
    }
	
    @Test
	public void testAddToCart() throws IOException, Exception {
		
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/adicionar")
                .content(this.json(cartTO))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isCreated());
	}
    

    @Test
	public void testAddToCartCampoObrigatorio() throws IOException, Exception {
		
    	cartTO.setQ(0);
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/adicionar")
                .content(this.json(cartTO))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagens[0]", Matchers.is(Constants.QUANTITY_NOT_FOUND)));
	}

    @Test
	public void testFindCartNotFound() throws IOException, Exception {
		
    	cartTO.setCartId(UUID.randomUUID().toString());
    	
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + cartTO.getCartId())
                .content(this.json(cartTO))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagens[0]", Matchers.is(Constants.CART_NOT_FOUND)));
	}

    @Test
	public void testFindCart() throws IOException, Exception {
		
    	Cart cart = shoppingCart.addItem(cartTO);    	
    	
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + cart.getCartId())
                .content(this.json(cartTO))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Matchers.is(cart.getCartId())));
	}

    
    @SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
