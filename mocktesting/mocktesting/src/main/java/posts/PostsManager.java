package posts;

import client.VkClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostsManager {
    private final VkClient client;

    private final int HOUR = 3600;

    public PostsManager(VkClient client) {
        this.client = client;
    }

    public List<Long> getPostFrequency(String hashtag, int nHours) {
        assert (1 <= nHours && nHours <= 24) : "Invalid number of hours passed";

        List<List<Integer>> dateBounds = new ArrayList<>();
        int now = (int) (System.currentTimeMillis() / 1000);
        for (int time = now - nHours * HOUR; time < now; time += HOUR) {
            dateBounds.add(Arrays.asList(time, time + HOUR));
        }
        return dateBounds.stream()
                .mapToLong(bounds -> client.getInfo(hashtag, bounds.get(0), bounds.get(1)).totalCount)
                .boxed()
                .collect(Collectors.toList());
    }
}
