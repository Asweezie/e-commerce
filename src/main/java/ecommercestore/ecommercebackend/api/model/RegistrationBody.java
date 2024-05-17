package ecommercestore.ecommercebackend.api.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationBody {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank
    @NotNull
    @Email
    private String email;
    @Size(min = 8, max = 50)
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one number")
    private String password;
    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;

}
