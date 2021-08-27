//,temp,DigitalOceanDropletsProducer.java,363,367,temp,DigitalOceanAccountProducer.java,33,38
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        Account accountInfo = getEndpoint().getDigitalOceanClient().getAccountInfo();
        LOG.trace("Account [{}] ", accountInfo);
        exchange.getMessage().setBody(accountInfo);
    }

};