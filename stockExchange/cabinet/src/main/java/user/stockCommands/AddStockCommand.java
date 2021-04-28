package user.stockCommands;

import lombok.SneakyThrows;
import user.model.Stock;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class AddStockCommand extends StockCommand<Stock> {

    private final Long price;
    private final Long count;

    public AddStockCommand(String company, Long price, Long count) {
        super(company);
        this.price = price;
        this.count = count;
    }

    @Override
    public String getAddress() {
        return "";
    }

    @Override
    @SneakyThrows
    public Stock execute() {
        var stock = new Stock(getCompany(), price, count);
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(Resources.getObjectMapper().writeValueAsString(stock)))
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(Stock.class).readValue(response.body());
    }

}
