package user.stockCommands;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import user.userCommands.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class StockCommandTest {

    @Container
    private final GenericContainer<?> __ = new FixedHostPortGenericContainer<>("docker.io/library/stocks:1.0")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    @Test
    public void canDeposit() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        assertEquals(0L, user.getMoney());
        var success = new DepositCommand(login, 200L).execute();
        assertTrue(success);
        user = new AuthorizeCommand(login).execute();
        assertEquals(200L, user.getMoney());
    }

    @Test
    public void canNotDepositNotRegistered() {
        var login = "login";
        var command = new DepositCommand(login, 200L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotDepositInvalidMoneyValue() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        assertEquals(0L, user.getMoney());
        var command = new DepositCommand(login, -200L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotDepositMoneyOverflow() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        assertEquals(0L, user.getMoney());
        var command = new DepositCommand(login, 50001L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canAddStock() {
        var company = "company";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        assertEquals(company, stock.getCompany());
    }

    @Test
    public void canNotAddStockTwice() {
        var company = "company";
        var command = new AddStockCommand(company, 1L, 1L);
        command.execute();
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotAddStockInvalidPrice() {
        var company = "company";
        var command = new AddStockCommand(company, -1L, 1L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotAddStockInvalidCount() {
        var company = "company";
        var command = new AddStockCommand(company, 1L, -1L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotBuyStockNotRegistered() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var command = new BuyStocksCommand(login, company, 1L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotBuyStockIfNotExists() {
        var company = "company";
        var login = "login";
        var command = new BuyStocksCommand(login, company, 1L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotBuyStockInsufficientFunds() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var user = new RegisterUserCommand(login).execute();
        var command = new BuyStocksCommand(login, company, 1L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotBuyStockNotEnoughStock() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var user = new RegisterUserCommand(login).execute();
        var success = new DepositCommand(login, 1L).execute();
        assertTrue(success);
        var command = new BuyStocksCommand(login, company, 2L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotSellStockNotRegistered() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var command = new SellStocksCommand(login, company, 1L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotSellStockIfNotExists() {
        var company = "company";
        var login = "login";
        var command = new SellStocksCommand(login, company, 1L);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void canNotSellStockNotEnoughStock() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var user = new RegisterUserCommand(login).execute();
        var command = new SellStocksCommand(login, company, 1L);
        assertThrows(RuntimeException.class, command::execute);
    }
}