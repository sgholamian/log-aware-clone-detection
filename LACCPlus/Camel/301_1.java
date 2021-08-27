//,temp,VertxHttpProducerLoadTest.java,53,78,temp,HttpProducerLoadTest.java,81,106
//,2
public class xxx {
    @Test
    public void testProducerLoad() throws Exception {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < 40; i++) {
            map.put("mykey" + i, "myvalue" + i);
        }

        StopWatch watch = new StopWatch();

        // do not use template but reuse exchange/producer to be light-weight
        // and not create additional objects in the JVM as we want to analyze
        // the "raw" http producer
        Endpoint to = getMandatoryEndpoint("direct:echo");
        Producer producer = to.createProducer();
        producer.start();

        Exchange exchange = to.createExchange();
        exchange.getMessage().setHeaders(map);
        for (int i = 0; i < 10000000; i++) {
            exchange.getMessage().setBody("Message " + i);
            producer.process(exchange);
        }
        producer.stop();

        LOG.info("Took {} ms", watch.taken());
    }

};