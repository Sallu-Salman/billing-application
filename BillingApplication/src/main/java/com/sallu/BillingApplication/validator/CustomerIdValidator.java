package com.sallu.BillingApplication.validator;

import com.sallu.BillingApplication.entity.Contact;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomerIdValidator implements ConstraintValidator<IdNotZero, Contact> {
    @Override
    public void initialize(IdNotZero constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Contact contact, ConstraintValidatorContext constraintValidatorContext) {
        if(contact.getContactId() == 0) {
            return false;
        }
        return true;
    }
}
