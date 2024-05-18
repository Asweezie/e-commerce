package ecommercestore.ecommercebackend.api.controller.auth;

import ecommercestore.ecommercebackend.api.model.LoginBody;
import ecommercestore.ecommercebackend.api.model.LoginResponse;
import ecommercestore.ecommercebackend.api.model.RegistrationBody;
import ecommercestore.ecommercebackend.exception.UserAlreadyExistsException;
import ecommercestore.ecommercebackend.model.LocalUser;
import ecommercestore.ecommercebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponse response = new LoginResponse();
        response.setJwt(jwt);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        return user;
    }

}
