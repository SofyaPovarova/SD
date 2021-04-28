package user.stockCommands;

import lombok.SneakyThrows;
import user.model.Stock;
import user.util.HttpUtils;
import user.util.Resources;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class UpdatePriceCommand extends StockCommand<Stock> {

    private final Long price;

    public UpdatePriceCommand(String company, Long price) {
        super(company);
        this.price = price;
    }

    @Override
    public String getAddress() {
        return String.format("/%s?price=%d", getCompany(), price);
    }

    @Override
    @SneakyThrows
    public Stock execute() {
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(Stock.class).readValue(response.body());
    }

}
