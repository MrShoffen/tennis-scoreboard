package org.mrshoffen.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.mrshoffen.validator.ValidDifferentNames;

@Data

@ValidDifferentNames(message = "Players should not be the same!")
public class NewMatchRequestDto {

    @Size(min = 4, max = 30, message = "Incorrect name length! Must be between {min} and {max}")
    @NotNull(message = "Name can't be null!")
    @Pattern(regexp =  "^[a-zA-Z]+[a-zA-Z-. ]*[a-zA-Z.]+$", message = "Incorrect symbols in name!")
    String firstPlayer;

    @Size(min = 4, max = 30, message = "Incorrect name length! Must be between {min} and {max}")
    @NotNull(message = "Name can't be null!")
    @Pattern(regexp =  "^[a-zA-Z]+[a-zA-Z-. ]*[a-zA-Z.]+$", message = "Incorrect symbols in name!")
    String secondPlayer;
}
