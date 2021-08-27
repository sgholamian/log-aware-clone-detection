//,temp,RecipientListWithInterceptorTest.java,44,67,temp,RoutingSlipWithInterceptorTest.java,44,67
//,2
public class xxx {
        @Override
        public Processor wrapProcessorInInterceptors(
                final CamelContext context, final NamedNode definition, final Processor target, final Processor nextTarget)
                throws Exception {
            if (definition instanceof RoutingSlipDefinition<?>) {
                final DelegateAsyncProcessor delegateAsyncProcessor = new DelegateAsyncProcessor() {

                    @Override
                    public boolean process(final Exchange exchange, final AsyncCallback callback) {
                        LOGGER.info("I'm doing someting");
                        return super.process(exchange, new AsyncCallback() {
                            public void done(final boolean doneSync) {
                                LOGGER.info("I'm done");
                                doneCount++;
                                callback.done(doneSync);
                            }
                        });
                    }
                };
                delegateAsyncProcessor.setProcessor(target);
                return delegateAsyncProcessor;
            }
            return new DelegateAsyncProcessor(target);
        }

};