//,temp,ContainerWideInterceptor.java,33,55,temp,AdviceWithStartTargetIssueTest.java,74,93
//,3
public class xxx {
        @Override
        public Processor wrapProcessorInInterceptors(
                final CamelContext context, final NamedNode definition, final Processor target, final Processor nextTarget)
                throws Exception {

            return new DelegateAsyncProcessor(new Processor() {

                public void process(Exchange exchange) throws Exception {
                    // we just count number of interceptions
                    count++;
                    LOG.info("I am the container wide interceptor. Intercepted total count: " + count);
                    target.process(exchange);
                }

                @Override
                public String toString() {
                    return "ContainerWideInterceptor[" + target + "]";
                }
            });
        }

};