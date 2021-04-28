package user.userCommands;

import lombok.SneakyThrows;
import user.model.User;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class AuthorizeCommand extends UserCommand<User> {

    public AuthorizeCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return String.format("/%s", getLogin());
    }

    @Override
    @SneakyThrows
    public User execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .GET()
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(User.class).readValue(response.body());
    }

}
