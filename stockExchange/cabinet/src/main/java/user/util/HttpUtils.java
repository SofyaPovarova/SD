package user.util;

import lombok.SneakyThrows;

import java.net.http.HttpResponse;

public class HttpUtils {

    @SneakyThrows
    public static void checkResponseCode(HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            return;
        }
        var error = Resources.getObjectMapper().readValue(response.body(), Error.class);
        throw new RuntimeException(error.getMessage());
    }

}
