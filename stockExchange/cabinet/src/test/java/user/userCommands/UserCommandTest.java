package user.userCommands;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserCommandTest {

    @Container
    private final GenericContainer<?> __ = new FixedHostPortGenericContainer<>("docker.io/library/stocks:1.0")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    @Test
    public void canRegister() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        assertEquals(login, user.getLogin());
    }

    @Test
    public void canNotRegisterTwice() {
        var login = "login";
        var command = new RegisterUserCommand(login);
        command.execute();
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canAuthorize() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        var authorizedUser = new AuthorizeCommand(login).execute();
        assertEquals(user.getLogin(), authorizedUser.getLogin());
    }

    @Test
    public void canNotAuthorizeIfNotExists() {
        var login = "login";
        var command = new AuthorizeCommand(login);
        assertThrows(RuntimeException.class, command::execute);
    }

}