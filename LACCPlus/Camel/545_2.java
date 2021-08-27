//,temp,WordpressPostProducer.java,42,46,temp,WordpressUserProducer.java,40,44
//,3
public class xxx {
    @Override
    protected User processUpdate(Exchange exchange) {
        LOG.debug("Trying to update the user {} with id {}", exchange.getIn().getBody(), this.getConfiguration().getId());
        return serviceUsers.update(getConfiguration().getId(), exchange.getIn().getBody(User.class));
    }

};