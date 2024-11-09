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

    @Size(min = 4, max = 30, message = "Incorrect name length! Must be between {min} and {max}")
    @NotNull(message = "Name can't be null!")
    @Pattern(regexp =  "^[a-zA-Z]+[a-zA-Z-. ]*[a-zA-Z.]+$", message = "Incorrect symbols in name!")
    private String pointWinner;


    public static void main(String[] args) {


        NewMatchRequestDto newMatchRequestDto = new NewMatchRequestDto();
        newMatchRequestDto.setFirstPlayer("fder");
        newMatchRequestDto.setSecondPlayer("fder");

        Validator validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory()
                .getValidator();

        var res = validator.validate(newMatchRequestDto);

        System.out.println(res);
    }
}


