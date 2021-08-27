//,temp,NsqProducer.java,46,53,temp,NettyUdpWithInOutUsingPlainSocketTest.java,71,78
//,3
public class xxx {
                    public void process(Exchange exchange) throws Exception {
                        String s = exchange.getIn().getBody(String.class);
                        LOG.debug("Server got: " + s);
                        exchange.getOut().setBody("Hello " + s);
                        // just make the remote address is there
                        assertNotNull(exchange.getIn().getHeader(NettyConstants.NETTY_REMOTE_ADDRESS),
                                "The remote address header should not be Null");
                    }

};