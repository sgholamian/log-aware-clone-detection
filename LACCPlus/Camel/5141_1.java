//,temp,SimpleMessagingExample.java,49,103,temp,DynamicRoutingExample.java,48,116
//,3
public class xxx {
    public void sendMessage() throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();

        final CountDownLatch logonLatch = new CountDownLatch(2);
        final CountDownLatch receivedMessageLatch = new CountDownLatch(1);

        RouteBuilder routes = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // Release latch when session logon events are received
                // We expect two events, one for the trader session and one for the market session
                from("quickfix:examples/inprocess.cfg")
                        .filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.SessionLogon))
                        .bean(new CountDownLatchDecrementer("logon", logonLatch));

                // For all received messages, print the JSON-formatted message to stdout
                from("quickfix:examples/inprocess.cfg").filter(PredicateBuilder.or(
                        header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AdminMessageReceived),
                        header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived)))
                        .bean(new QuickfixjMessageJsonPrinter());

                // If the market session receives an email then release the latch
                from("quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:MARKET->TRADER")
                        .filter(header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL))
                        .bean(new CountDownLatchDecrementer("message", receivedMessageLatch));
            }
        };

        context.addRoutes(routes);

        LOG.info("Starting Camel context");
        context.start();

        if (!logonLatch.await(5L, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Logon did not succeed");
        }

        String marketUri = "quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:TRADER->MARKET";
        Producer producer = context.getEndpoint(marketUri).createProducer();

        Email email = TestSupport.createEmailMessage("Example");
        Exchange exchange = producer.getEndpoint().createExchange(ExchangePattern.InOnly);
        exchange.getIn().setBody(email);
        producer.process(exchange);

        if (!receivedMessageLatch.await(5L, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Message did not reach market");
        }

        LOG.info("Message received, shutting down Camel context");

        context.stop();

        LOG.info("Example complete");
    }

};