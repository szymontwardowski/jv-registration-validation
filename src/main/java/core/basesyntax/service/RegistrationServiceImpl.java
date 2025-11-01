package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new UserValidationException("User cannot be null.");
        }

        if (user.getLogin() == null) {
            throw new UserValidationException("Login cannot be null.");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password cannot be null.");
        }
        if (user.getAge() == null) {
            throw new UserValidationException("Age cannot be null.");
        }

        if (user.getLogin().length() < 6) {
            throw new UserValidationException("Login must be at least 6 characters long.");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new UserValidationException("Login already exist.");
        }

        if (user.getAge() < 18) {
            throw new UserValidationException("You must be 18 or older.");
        }

        if (user.getPassword().length() < 6) {
            throw new UserValidationException("Password must be at least 6 characters long.");
        }

        return storageDao.add(user);
    }
}
