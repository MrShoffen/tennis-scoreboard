package org.mrshoffen.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mrshoffen.dto.request.NewMatchRequestDto;

public class DifferentNamesValidator implements ConstraintValidator<ValidDifferentNames, NewMatchRequestDto> {

    @Override
    public boolean isValid(NewMatchRequestDto value, ConstraintValidatorContext context) {

        return !value.getFirstPlayer().equals(value.getSecondPlayer());

    }
}
