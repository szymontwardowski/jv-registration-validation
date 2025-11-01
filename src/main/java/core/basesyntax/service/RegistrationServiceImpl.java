package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        // 1. Walidacja: sam obiekt User nie może być null
        if (user == null) {
            throw new UserValidationException("User cannot be null.");
        }

        // 2. Walidacja: pola wewnętrzne nie mogą być null
        if (user.getLogin() == null) {
            throw new UserValidationException("Login cannot be null.");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password cannot be null.");
        }
        if (user.getAge() == null) {
            throw new UserValidationException("Age cannot be null.");
        }

        // 3. Walidacja: Długość Loginu (granica 6)
        if (user.getLogin().length() < 6) {
            throw new UserValidationException("Login must be at least 6 characters long.");
        }

        // 4. Walidacja: Duplikat
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new UserValidationException("Login already exist.");
        }

        // 5. Walidacja: Wiek (granica 18)
        if (user.getAge() < 18) {
            throw new UserValidationException("You must be 18 or older.");
        }

        // 6. Walidacja: Długość Hasła (granica 6)
        if (user.getPassword().length() < 6) {
            throw new UserValidationException("Password must be at least 6 characters long.");
        }

        return storageDao.add(user);
    }
}