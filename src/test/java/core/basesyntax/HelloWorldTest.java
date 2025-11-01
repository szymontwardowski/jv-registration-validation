package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    public void setUp() {
        storageDao.clear();
    }

    @Test
    public void register_userIsNull_throwsException() { // Zgodne z konwencją
        assertThrows
                (UserValidationException.class,
                        () -> registrationService.register(null)
                );
    }

    @Test
    public void register_loginIsNull_throwsException() { // Zgodne z konwencją
        User user = new User();
        user.setPassword("password123");
        user.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_passwordIsNull_throwsException() { // Zgodne z konwencją
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_ageIsNull_throwsException() { // Zgodne z konwencją
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_loginTooShort_throwsException() { // Zgodne z konwencją
        User user = new User();
        user.setLogin("abcde");
        user.setPassword("password123");
        user.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_passwordTooShort_throwsException() { // Zgodne z konwencją
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345");
        user.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_userUnderage_throwsException() { // Zgodne z konwencją
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(17);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_loginAlreadyExists_throwsException() {
        User firstUser = new User();
        firstUser.setLogin("uniqueLogin");
        firstUser.setPassword("password123");
        firstUser.setAge(20);
        storageDao.add(firstUser);

        User duplicateUser = new User();
        duplicateUser.setLogin("uniqueLogin");
        duplicateUser.setPassword("password456");
        duplicateUser.setAge(25);

        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(duplicateUser)
        );
    }

    @Test
    public void register_validUser_returnsRegisteredUser() { // Zgodne z konwencją
        User newUser = new User();
        newUser.setLogin("validlogin6");
        newUser.setPassword("password123");
        newUser.setAge(18);
        User registeredUser = registrationService.register(newUser);

        assertNotNull(registeredUser);

        // Asercja zapisu (przeszedł w poprzedniej weryfikacji)
        User userFromStorage = storageDao.get("validlogin6");
        assertNotNull(userFromStorage, "Registered user should be found in storage.");

        assertEquals("validlogin6", userFromStorage.getLogin());
        assertEquals(18, registeredUser.getAge());
    }
}
