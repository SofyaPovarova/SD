package posts;

import java.util.List;

public class PostsInfo {
    public final List<Post> postsPage;

    public final long totalCount;

    public PostsInfo(List<Post> postsPage, long totalCount) {
        this.postsPage = postsPage;
        this.totalCount = totalCount;
    }
}
