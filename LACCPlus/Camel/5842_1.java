//,temp,ContainerWideInterceptor.java,33,55,temp,AdviceWithStartTargetIssueTest.java,74,93
//,3
public class xxx {
    @Override
    public Processor wrapProcessorInInterceptors(
            final CamelContext context, final NamedNode definition,
            final Processor target, final Processor nextTarget)
            throws Exception {

        // as this is based on an unit test we are a bit lazy and just create an inlined processor
        // where we implement our interception logic.
        return new Processor() {
            public void process(Exchange exchange) throws Exception {
                // we just count number of interceptions
                count++;
                LOG.info("I am the container wide interceptor. Intercepted total count: " + count);
                // its important that we delegate to the real target so we let target process the exchange
                target.process(exchange);
            }

            @Override
            public String toString() {
                return "ContainerWideInterceptor[" + target + "]";
            }
        };
    }

};