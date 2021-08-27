//,temp,SimpleMessagingExample.java,49,103,temp,DynamicRoutingExample.java,48,116
//,3
public class xxx {
    public void sendMessage() throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();

        final CountDownLatch logonLatch = new CountDownLatch(4);
        final CountDownLatch receivedMessageLatch = new CountDownLatch(1);

        RouteBuilder routes = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // Release latch when session logon events are received
                // We expect four logon events (four sessions)
                from("quickfix:examples/gateway.cfg")
                        .filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.SessionLogon))
                        .bean(new CountDownLatchDecrementer("logon", logonLatch));

                // Dynamic router -- Uses FIX DeliverTo tags
                from("quickfix:examples/gateway.cfg")
                        .filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY)
                                .isEqualTo(QuickfixjEventCategory.AppMessageReceived))
                        .recipientList(method(new FixMessageRouter("quickfix:examples/gateway.cfg")));

                // Logger app messages as JSON
                from("quickfix:examples/gateway.cfg").filter(PredicateBuilder.or(
                        header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived),
                        header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageSent)))
                        .bean(new QuickfixjMessageJsonPrinter());

                // If the trader@2 session receives an email then release the latch
                from("quickfix:examples/gateway.cfg?sessionID=FIX.4.2:TRADER@2->GATEWAY").filter(PredicateBuilder.and(
                        header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived),
                        header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL)))
                        .bean(new CountDownLatchDecrementer("message", receivedMessageLatch));
            }
        };

        context.addRoutes(routes);

        LOG.info("Starting Camel context");
        context.start();

        // This is not strictly necessary, but it prevents the need for session
        // synchronization due to app messages being sent before being logged on
        if (!logonLatch.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Logon did not complete");
        }

        String gatewayUri = "quickfix:examples/gateway.cfg?sessionID=FIX.4.2:TRADER@1->GATEWAY";
        Endpoint gatewayEndpoint = context.getEndpoint(gatewayUri);
        Producer producer = gatewayEndpoint.createProducer();

        Email email = TestSupport.createEmailMessage("Dynamic Routing Example");
        email.getHeader().setString(DeliverToCompID.FIELD, "TRADER@2");

        LOG.info("Sending routed message");

        Exchange exchange = producer.getEndpoint().createExchange(ExchangePattern.InOnly);
        exchange.getIn().setBody(email);
        producer.process(exchange);

        if (!receivedMessageLatch.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Message did not reach target");
        }

        LOG.info("Message received, shutting down Camel context");

        context.stop();

        LOG.info("Dynamic routing example complete");
    }

};