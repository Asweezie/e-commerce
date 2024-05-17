package ecommercestore.ecommercebackend.service;

import ecommercestore.ecommercebackend.api.model.LoginBody;
import ecommercestore.ecommercebackend.api.model.RegistrationBody;
import ecommercestore.ecommercebackend.exception.UserAlreadyExistsException;
import ecommercestore.ecommercebackend.model.LocalUser;
import ecommercestore.ecommercebackend.model.dao.LocalUserDAO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final LocalUserDAO localUserDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(@Valid RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser localUser = new LocalUser();
        localUser.setFirstName(registrationBody.getFirstName());
        localUser.setLastName(registrationBody.getLastName());
        localUser.setEmail(registrationBody.getEmail());
        localUser.setUsername(registrationBody.getUsername());

        localUser.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

        return localUserDAO.save(localUser);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> localUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());

        if (localUser.isEmpty()) {
            return null;
        }

        LocalUser user = localUser.get();
        if (!encryptionService.checkPassword(loginBody.getPassword(), user.getPassword())) {
            return null;
        }

        return jwtService.generateJWT(user);
    }

}
