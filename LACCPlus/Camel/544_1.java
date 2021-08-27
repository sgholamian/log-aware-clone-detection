//,temp,WordpressPostProducer.java,36,40,temp,AlbertoAggregatorTest.java,129,132
//,3
public class xxx {
    @Override
    protected Post processInsert(Exchange exchange) {
        LOG.debug("Trying to create a new blog post with {}", exchange.getIn().getBody());
        return servicePosts.create(exchange.getIn().getBody(Post.class));
    }

};