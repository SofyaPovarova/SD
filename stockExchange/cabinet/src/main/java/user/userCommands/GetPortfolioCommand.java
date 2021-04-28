package user.userCommands;

import lombok.SneakyThrows;
import user.model.Stock;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;


public class GetPortfolioCommand extends UserCommand<List<Stock>> {

    public GetPortfolioCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return String.format("/portfolio/%s", getLogin());
    }

    @Override
    @SneakyThrows
    public List<Stock> execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .GET()
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Arrays.asList(Resources.getObjectMapper().readerFor(Stock[].class).readValue(response.body()));
    }

}
