package user.userCommands;

import lombok.SneakyThrows;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class DepositCommand extends UserCommand<Boolean> {

    private final Long money;

    public DepositCommand(String login, Long money) {
        super(login);
        this.money = money;
    }

    @Override
    public String getAddress() {
        return String.format("/deposit/%s?value=%d", getLogin(), money);
    }

    @Override
    @SneakyThrows
    public Boolean execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return true;
    }

}
