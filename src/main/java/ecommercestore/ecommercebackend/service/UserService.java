package ecommercestore.ecommercebackend.service;

import ecommercestore.ecommercebackend.api.model.RegistrationBody;
import ecommercestore.ecommercebackend.exception.UserAlreadyExistsException;
import ecommercestore.ecommercebackend.model.LocalUser;
import ecommercestore.ecommercebackend.model.dao.LocalUserDAO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final LocalUserDAO localUserDAO;

    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
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

        localUser.setPassword(registrationBody.getPassword());

        return localUserDAO.save(localUser);
    }

}
