package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;

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

        // Użycie stałej MIN_LENGTH
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserValidationException("Login must be at least " + MIN_LENGTH + " characters long.");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new UserValidationException("Login already exist.");
        }

        // Użycie stałej MIN_AGE
        if (user.getAge() < MIN_AGE) {
            throw new UserValidationException("You must be " + MIN_AGE + " or older.");
        }

        // Użycie stałej MIN_LENGTH
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserValidationException("Password must be at least " + MIN_LENGTH + " characters long.");
        }

        return storageDao.add(user);
    }
}
