package user.userCommands;

import lombok.SneakyThrows;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class BuyStocksCommand extends UserCommand<Boolean> {

    private final String company;
    private final Long count;

    public BuyStocksCommand(String login, String company, Long count) {
        super(login);
        this.company = company;
        this.count = count;
    }

    @Override
    public String getAddress() {
        return String.format("/buy/%s?company=%s&count=%d", getLogin(), company, count);
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
