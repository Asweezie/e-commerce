package ecommercestore.ecommercebackend.api.controller.auth;

import ecommercestore.ecommercebackend.api.model.RegistrationBody;
import ecommercestore.ecommercebackend.exception.UserAlreadyExistsException;
import ecommercestore.ecommercebackend.model.LocalUser;
import ecommercestore.ecommercebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {

        try {
            LocalUser user = userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
