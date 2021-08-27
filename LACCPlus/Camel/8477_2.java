//,temp,Pipeline.java,189,212,temp,CamelInternalProcessor.java,141,157
//,3
public class xxx {
    @Override
    protected void doBuild() throws Exception {
        boolean pooled = camelContext.adapt(ExtendedCamelContext.class).getExchangeFactory().isPooled();

        // only create pooled task factory
        if (pooled) {
            taskFactory = new CamelInternalPooledTaskFactory();
            int capacity = camelContext.adapt(ExtendedCamelContext.class).getExchangeFactory().getCapacity();
            taskFactory.setCapacity(capacity);
            LOG.trace("Using TaskFactory: {}", taskFactory);

            // create empty array we can use for reset
            emptyStatefulStates = new Object[statefulAdvices];
        }

        ServiceHelper.buildService(taskFactory, processor);
    }

};