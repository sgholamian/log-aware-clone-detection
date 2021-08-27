//,temp,Pipeline.java,189,212,temp,CamelInternalProcessor.java,141,157
//,3
public class xxx {
    @Override
    protected void doBuild() throws Exception {
        boolean pooled = camelContext.adapt(ExtendedCamelContext.class).getExchangeFactory().isPooled();
        if (pooled) {
            taskFactory = new PooledTaskFactory(getId()) {
                @Override
                public PooledExchangeTask create(Exchange exchange, AsyncCallback callback) {
                    return new PipelineTask();
                }
            };
            int capacity = camelContext.adapt(ExtendedCamelContext.class).getExchangeFactory().getCapacity();
            taskFactory.setCapacity(capacity);
        } else {
            taskFactory = new PrototypeTaskFactory() {
                @Override
                public PooledExchangeTask create(Exchange exchange, AsyncCallback callback) {
                    return new PipelineTask();
                }
            };
        }
        LOG.trace("Using TaskFactory: {}", taskFactory);

        ServiceHelper.buildService(taskFactory, processors);
    }

};