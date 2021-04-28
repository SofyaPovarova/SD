package user.userCommands;

import lombok.SneakyThrows;
import user.model.User;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class RegisterUserCommand extends UserCommand<User> {

    public RegisterUserCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return String.format("?login=%s", getLogin());
    }

    @SneakyThrows
    @Override
    public User execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(User.class).readValue(response.body());
    }

}
