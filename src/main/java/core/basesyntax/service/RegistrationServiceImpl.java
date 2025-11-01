package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new UserValidationException("user cannot be null");
        }

        if (user.getLogin().length() < 6) {
            throw new UserValidationException("Login too short");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new UserValidationException("Login already exist");
        }

        if (user.getAge() < 18) {
            throw new UserValidationException("You must be 18");
        }

        if (user.getPassword().length() < 6) {
            throw new UserValidationException("Password is too short");
        }

        return storageDao.add(user);
    }
}
