package user.stockCommands;

import lombok.Data;
import user.Command;

@Data
public abstract class StockCommand<T> implements Command<T> {

    private final String company;

    public String getBaseUrl() {
        return "/stock";
    }

}
