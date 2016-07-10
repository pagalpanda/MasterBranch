package common.barter.com.barterapp.data.repository;

/**
 * Created by vikram on 26/06/16.
 */
public interface PostRepository {
    void getUserPosts(long userId);
    void getPrimaryImage(long postId);
    void getAllImages(long postId);
}
