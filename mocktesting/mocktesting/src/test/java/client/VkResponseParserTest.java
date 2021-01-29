package client;

import org.junit.Assert;
import org.junit.Test;
import posts.Post;
import posts.PostsInfo;

import java.util.Arrays;
import java.util.List;

public class VkResponseParserTest {
    private final static String testResponse =
            "{\n" +
                    "  \"response\": {\n" +
                    "    \"items\": [\n" +
                    "      {\n" +
                    "        \"id\": 11111,\n" +
                    "        \"date\": 1603739584,\n" +
                    "        \"owner_id\": 234848849,\n" +
                    "        \"from_id\": 234848849,\n" +
                    "        \"post_type\": \"post\",\n" +
                    "        \"text\": \"Special price\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 4242,\n" +
                    "        \"date\": 1603739583,\n" +
                    "        \"owner_id\": 932300232,\n" +
                    "        \"from_id\": 620806556,\n" +
                    "        \"post_id\": 2758,\n" +
                    "        \"parents_stack\": [],\n" +
                    "        \"post_type\": \"reply\",\n" +
                    "        \"text\": \"Salesalesale\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 12345,\n" +
                    "        \"date\": 1603739583,\n" +
                    "        \"owner_id\": 123456789,\n" +
                    "        \"from_id\": 587332271,\n" +
                    "        \"post_id\": 2046853,\n" +
                    "        \"parents_stack\": [],\n" +
                    "        \"post_type\": \"reply\",\n" +
                    "        \"text\": \"Big sale!\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"total_count\": 54661\n" +
                    "  }\n" +
                    "}\n";

    @Test
    public void parseResponse() throws Exception {
        VkResponseParser parser = new VkResponseParser();
        PostsInfo info = parser.parseResponse(testResponse);

        Assert.assertTrue(info.totalCount == 54661);

        List<Post> posts = Arrays.asList(
                new Post(11111, 1603739584, 234848849, 234848849, "post", "Special price"),
                new Post(4242, 1603739583, 932300232, 620806556, "reply", "Salesalesale"),
                new Post(12345, 1603739583, 123456789, 587332271, "reply", "Big sale!")
        );

        for (int i = 0; i < posts.size(); i++) {
            Assert.assertEquals(info.postsPage.get(i), posts.get(i));
        }
    }
}