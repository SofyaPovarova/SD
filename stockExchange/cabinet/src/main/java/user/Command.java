package user;

import java.net.URI;

public interface Command<T> {

    T execute();

    String getAddress();

    String getBaseUrl();

    default URI getUri() {
        return URI.create(String.format("http://localhost:8080%s%s", getBaseUrl(), getAddress()));
    }

}
