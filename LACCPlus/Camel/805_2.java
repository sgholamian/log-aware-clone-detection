//,temp,WordpressServicePostsAdapter.java,66,72,temp,WordpressServicePagesAdapter.java,67,72
//,3
public class xxx {
    @Override
    public Page retrieve(Integer pageId, Context context, String password) {
        LOGGER.debug("Calling retrieve: postId {};  context: {}", pageId, context);
        checkArgument(pageId > 0, "Please provide a non zero post id");
        return getSpi().retrieve(this.getApiVersion(), pageId, context, password);
    }

};