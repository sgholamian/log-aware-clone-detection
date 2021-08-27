//,temp,FileRenameFileOnCommitIssueTest.java,41,58,temp,JmsInOutFixedReplyQueueTimeoutUseMessageIDAsCorrelationIDTest.java,27,46
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(fileUri("renameissue?noop=false&initialDelay=0&delay=10"))
                        .setProperty("PartitionID")
                        .simple("${file:name}").convertBodyTo(String.class)
                        .to(ExchangePattern.InOut, "direct:source").process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                log.info("The exchange's IN body as String is {}", exchange.getIn().getBody(String.class));
                            }
                        }).to("mock:result");

                from("direct:source").transform(body().prepend("Hello "));
            }
        };
    }

};