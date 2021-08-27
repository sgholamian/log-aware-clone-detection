//,temp,JmsInOutIndividualRequestTimeoutTest.java,90,103,temp,JmsInOutFixedReplyQueueTimeoutTest.java,78,91
//,2
public class xxx {
            public void configure() throws Exception {
                from("direct:start")
                        .to(ExchangePattern.InOut, "activemq:queue:foo?replyTo=queue:bar&requestTimeout=2000")
                        .to("mock:result");

                from("activemq:queue:foo")
                        .process(exchange -> {
                            String body = exchange.getIn().getBody(String.class);
                            if ("World".equals(body)) {
                                log.debug("Sleeping for 4 sec to force a timeout");
                                Thread.sleep(4000);
                            }
                        }).transform(body().prepend("Bye ")).to("log:reply");
            }

};