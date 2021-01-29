package posts;

import client.VkClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class PostsManagerTest {
    private PostsManager postManager;

    @Mock
    private VkClient client;

    @Before
    public void setUp() throws Exception {
        client = mock(VkClient.class);
        postManager = new PostsManager(client);
    }

    @Test
    public void getCompanyNamesWithGrowingPrice() {
        String hashtag = "Big";
        int nHours = 2;
        when(client.getInfo(anyString(), anyInt(), anyInt())).thenReturn(createAnswer());

        List<Long> frequency = postManager.getPostFrequency(hashtag, nHours);
        Assert.assertEquals(Arrays.asList(10L, 10L), frequency);
    }

    private PostsInfo createAnswer() {
        return new PostsInfo(Arrays.asList(
                new Post(12345, 1603739583, 123456789, 587332271, "reply", "Big sale!"),
                new Post(12345, 1603739583, 123456789, 587332271, "reply", "Big sale!")
        ), 10);
    }
}