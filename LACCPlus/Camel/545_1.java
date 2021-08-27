//,temp,WordpressPostProducer.java,42,46,temp,WordpressUserProducer.java,40,44
//,3
public class xxx {
    @Override
    protected Post processUpdate(Exchange exchange) {
        LOG.debug("Trying to update the post {} with id {}", exchange.getIn().getBody(), this.getConfiguration().getId());
        return servicePosts.update(this.getConfiguration().getId(), exchange.getIn().getBody(Post.class));
    }

};