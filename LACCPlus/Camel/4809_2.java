//,temp,JmsInOutExclusiveTopicTest.java,53,74,temp,JmsInOutExclusiveTopicRecipientListTest.java,54,75
//,3
public class xxx {
            public void configure() throws Exception {
                from("direct:start")
                        .recipientList().header("whereTo")
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