package reader;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import rule.HostReachableRule;


@HostReachableRule.HostReachable("api.vk.com")
public class UrlReaderTest {
    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void read() {
        String result = new UrlReader()
                .readAsText("http://api.vk.com/");
        Assert.assertTrue(result.length() > 0);
    }
}