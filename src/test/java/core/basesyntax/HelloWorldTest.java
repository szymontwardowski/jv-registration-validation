package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserValidationException;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    public void register_invalidData_throwsException() {
        // Test na za krotki login
        User shortLoginUser = new User();
        shortLoginUser.setLogin("abc"); // mniej niż 6 znakow
        shortLoginUser.setPassword("password123");
        shortLoginUser.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(shortLoginUser)
        );

        // Test na za mlody wiek
        User underageUser = new User();
        underageUser.setLogin("validLogin");
        underageUser.setPassword("password123");
        underageUser.setAge(17); // ponizej 18
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(underageUser)
        );

        // Test na za krotkie haslo
        User shortPasswordUser = new User();
        shortPasswordUser.setLogin("validLogin2");
        shortPasswordUser.setPassword("123"); // krótkie hasło
        shortPasswordUser.setAge(20);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(shortPasswordUser)
        );

        // Test na istniejacego uzytkownika
        User firstUser = new User();
        firstUser.setLogin("uniqueLogin");
        firstUser.setPassword("password123");
        firstUser.setAge(20);
        registrationService.register(firstUser);

        User duplicateUser = new User();
        duplicateUser.setLogin("uniqueLogin"); // ten sam login
        duplicateUser.setPassword("password456");
        duplicateUser.setAge(25);
        assertThrows(
                UserValidationException.class,
                () -> registrationService.register(duplicateUser)
        );
    }

    @Test
    public void register_validData_ok() {

        User newUser = new User();
        newUser.setId(1L);
        newUser.setLogin("validlogin");
        newUser.setPassword("password123");
        newUser.setAge(22);
        User registeredUser = registrationService.register(newUser);

        assertNotNull(registeredUser);

        assertEquals("validlogin", registeredUser.getLogin());

    }
}
