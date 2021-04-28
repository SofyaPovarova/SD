package user.stockCommands;

import lombok.SneakyThrows;
import user.model.Stock;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;


public class ListStockCommand extends StockCommand<List<Stock>> {

    public ListStockCommand(String company) {
        super(company);
    }

    @Override
    public String getAddress() {
        return "";
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
