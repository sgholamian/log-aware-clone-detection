//,temp,PulsarCustomMessageReceiptIT.java,108,117,temp,NettyUdpWithInOutUsingPlainSocketTest.java,69,80
//,3
public class xxx {
            public void configure() {
                from("netty:udp://127.0.0.1:{{port}}?textline=true&sync=true").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        String s = exchange.getIn().getBody(String.class);
                        LOG.debug("Server got: " + s);
                        exchange.getOut().setBody("Hello " + s);
                        // just make the remote address is there
                        assertNotNull(exchange.getIn().getHeader(NettyConstants.NETTY_REMOTE_ADDRESS),
                                "The remote address header should not be Null");
                    }
                });
            }

};