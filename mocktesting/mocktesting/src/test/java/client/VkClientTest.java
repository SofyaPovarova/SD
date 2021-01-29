package client;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import posts.PostsInfo;
import rule.HostReachableRule;

@HostReachableRule.HostReachable(VkClientTest.HOST)
public class VkClientTest {
    public static final String HOST = "api.vk.com";

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void getInfo() {
        VkClient client = new VkClient(HOST);
        PostsInfo infos = client.getInfo("Sale", 1611555000, 1611555536);
        Assert.assertNotNull(infos);
    }
}