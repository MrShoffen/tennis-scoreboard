package org.mrshoffen.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.HibernateValidator;

@Data
public class PointScoreDto {

    private String pointWinner;

}


