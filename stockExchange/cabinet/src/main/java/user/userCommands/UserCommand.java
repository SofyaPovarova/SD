package user.userCommands;

import lombok.Data;
import user.Command;

@Data
public abstract class UserCommand<T> implements Command<T> {

    private final String login;

    public String getBaseUrl() {
        return "/user";
    }

}
