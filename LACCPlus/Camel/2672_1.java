//,temp,ContainerWideInterceptor.java,42,48,temp,AdviceWithStartTargetIssueTest.java,81,86
//,2
public class xxx {
            public void process(Exchange exchange) throws Exception {
                // we just count number of interceptions
                count++;
                LOG.info("I am the container wide interceptor. Intercepted total count: " + count);
                // its important that we delegate to the real target so we let target process the exchange
                target.process(exchange);
            }

};