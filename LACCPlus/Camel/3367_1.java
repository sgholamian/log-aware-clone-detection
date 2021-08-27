//,temp,CustomIdFactoryTest.java,118,127,temp,DnsActivationPolicy.java,49,53
//,3
public class xxx {
                @Override
                protected void processNext(Exchange exchange) throws Exception {
                    LOG.debug("Debugging at: {} with id: {} with exchange: {}", definition, definition.getId(), exchange);

                    // record the path taken at runtime
                    ids += definition.getId();

                    // continue to the real target by invoking super
                    super.processNext(exchange);
                }

};