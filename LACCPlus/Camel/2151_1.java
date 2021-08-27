//,temp,JmsInOutExclusiveTopicTest.java,50,76,temp,JmsInOutExclusiveTopicRecipientListTest.java,51,77
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from("direct:start")
                        .to("activemq:topic:news?replyToType=Exclusive&replyTo=queue:back")
                        .to("mock:result");

                from("activemq:topic:news?disableReplyTo=true")
                        .transform(body().prepend("Bye "))
                        .process(exchange -> {
                            String replyTo = exchange.getIn().getHeader("JMSReplyTo", String.class);
                            String cid = exchange.getIn().getHeader("JMSCorrelationID", String.class);

                            log.info("ReplyTo: {}", replyTo);
                            log.info("CorrelationID: {}", cid);
                            if (replyTo != null && cid != null) {
                                // wait a bit before sending back
                                Thread.sleep(1000);
                                log.info("Sending back reply message on {}", replyTo);
                                template.sendBodyAndHeader("activemq:" + replyTo, exchange.getIn().getBody(),
                                        "JMSCorrelationID", cid);
                            }
                        });
            }
        };
    }

};