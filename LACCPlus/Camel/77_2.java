//,temp,DigitalOceanSizesProducer.java,33,38,temp,DigitalOceanRegionsProducer.java,33,38
//,2
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        Regions regions = getEndpoint().getDigitalOceanClient().getAvailableRegions(configuration.getPage());
        LOG.trace("All Regions : page {} [{}] ", regions.getRegions(), configuration.getPage());
        exchange.getMessage().setBody(regions.getRegions());
    }

};