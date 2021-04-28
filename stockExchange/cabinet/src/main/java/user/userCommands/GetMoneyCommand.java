package user.userCommands;

import lombok.SneakyThrows;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetMoneyCommand extends UserCommand<Long> {

    public GetMoneyCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return String.format("/money/%s", getLogin());
    }

    @Override
    @SneakyThrows
    public Long execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .GET()
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(Long.class).readValue(response.body());
    }

}
