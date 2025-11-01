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

    // Metoda @BeforeEach czyści magazyn przed KAŻDYM testem!
    @BeforeEach
    public void setUp() {
        storageDao.clear();
    }

    @Test
    public void shouldThrowException_whenUserIsNull() {
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(null)
        );
    }

    @Test
    public void shouldThrowException_whenLoginIsNull() {
        User user = new User();
        user.setPassword("password123");
        user.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void shouldThrowException_whenPasswordIsNull() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void shouldThrowException_whenAgeIsNull() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void shouldThrowException_whenLoginIsFiveChars() {
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
    public void shouldThrowException_whenPasswordIsFiveChars() {
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
    public void shouldThrowException_whenUserIsSeventeen() {
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
    public void shouldThrowException_whenLoginAlreadyExists() {
        // 1. Zarejestruj pierwszego użytkownika
        User firstUser = new User();
        firstUser.setLogin("uniqueLogin");
        firstUser.setPassword("password123");
        firstUser.setAge(20);
        registrationService.register(firstUser);

        // 2. Spróbuj zarejestrować duplikat
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
    public void shouldRegister_whenValidDataIsProvided() {
        User newUser = new User();
        newUser.setLogin("validlogin6");
        newUser.setPassword("password123");
        newUser.setAge(18); // Wartość graniczna wieku
        User registeredUser = registrationService.register(newUser);

        // Asercja 1: Został zwrócony obiekt User
        assertNotNull(registeredUser);

        // Asercja 2: Sprawdzenie zapisu w magazynie (KLUCZOWA ASERCJA!)
        User userFromStorage = storageDao.get("validlogin6");
        assertNotNull(userFromStorage, "Registered user should be found in storage.");

        // Asercja 3: Sprawdzenie poprawności danych
        assertEquals("validlogin6", userFromStorage.getLogin());
        assertEquals(18, registeredUser.getAge());
    }
}
