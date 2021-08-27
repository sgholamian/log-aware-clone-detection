//,temp,WordpressServicePostsAdapter.java,66,72,temp,WordpressServicePagesAdapter.java,67,72
//,3
public class xxx {
    @Override
    public Post retrieve(Integer postId, Context context, String password) {
        LOGGER.debug("Calling retrievePosts: postId {};  postContext: {}", postId, context);
        checkArgument(postId > 0, "Please provide a non zero post id");
        checkNotNull(context, "Provide a post context");
        return getSpi().retrieve(this.getApiVersion(), postId, context, password);
    }

};