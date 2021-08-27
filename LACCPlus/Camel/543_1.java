//,temp,PrototypeExchangeFactory.java,50,61,temp,PooledExchangeFactory.java,44,54
//,3
public class xxx {
    @Override
    protected void doBuild() throws Exception {
        super.doBuild();
        this.exchangeFactoryManager = camelContext.adapt(ExtendedCamelContext.class).getExchangeFactoryManager();
        // force to create and load the class during build time so the JVM does not
        // load the class on first exchange to be created
        DefaultExchange dummy = new DefaultExchange(camelContext);
        // force message init to load classes
        dummy.getIn();
        dummy.getIn().getHeaders();
        LOG.trace("Warming up PrototypeExchangeFactory loaded class: {}", dummy.getClass().getName());
    }

};