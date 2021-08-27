//,temp,WordpressUserProducer.java,46,50,temp,ExecDocumentationExamplesTest.java,155,164
//,3
public class xxx {
    @Override
    protected User processInsert(Exchange exchange) {
        LOG.debug("Trying to create a new user{}", exchange.getIn().getBody());
        return serviceUsers.create(exchange.getIn().getBody(User.class));
    }

};