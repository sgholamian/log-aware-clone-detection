//,temp,PulsarConsumerReadCompactedIT.java,127,136,temp,NettyConsumerClientModeReconnectTest.java,86,97
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                from("netty:tcp://localhost:{{port}}?textline=true&clientMode=true&reconnect=true&reconnectInterval=200")
                        .id("client")
                        .process(new Processor() {
                            public void process(final Exchange exchange) {
                                log.info("Processing exchange in Netty server {}", exchange);
                                String body = exchange.getIn().getBody(String.class);
                                exchange.getOut().setBody("Bye " + body);
                            }
                        }).to("log:receive").to("mock:receive").noAutoStartup();
            }

};