package br.com.avaliacao.checkout.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.avaliacao.checkout.to.CartTO;

public class CartValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {		
		return CartTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
				
		ValidationUtils.rejectIfEmpty(errors, "codeProduct", "field.required.to.codeProduct");
		ValidationUtils.rejectIfEmpty(errors, "nameProduct", "field.required.to.nameProduct");
		ValidationUtils.rejectIfEmpty(errors, "brand", "field.required.to.brand");
		ValidationUtils.rejectIfEmpty(errors, "price", "field.required.to.price");

		CartTO cartTO = (CartTO) object;
		if (cartTO.getQ() == null || cartTO.getQ() <= 0) {
			errors.rejectValue("q", "field.required.to.q");
		}
	}

}
