//,temp,DigitalOceanSizesProducer.java,33,38,temp,DigitalOceanRegionsProducer.java,33,38
//,2
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        Sizes sizes = getEndpoint().getDigitalOceanClient().getAvailableSizes(configuration.getPage());
        LOG.trace("All Sizes : page {} [{}] ", sizes.getSizes(), configuration.getPage());
        exchange.getMessage().setBody(sizes.getSizes());
    }

};