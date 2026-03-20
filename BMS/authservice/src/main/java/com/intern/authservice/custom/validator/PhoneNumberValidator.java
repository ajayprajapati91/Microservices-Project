package com.intern.authservice.custom.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber , String> {
    private static final String PHONE_PATTERN = "^\\+?[0-9]{10}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber == null) return false;
        return phoneNumber.matches(PHONE_PATTERN);
    }
}
