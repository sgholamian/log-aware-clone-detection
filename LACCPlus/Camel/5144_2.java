//,temp,JpaWithNamedQueryAndParametersTest.java,64,113,temp,JpaTest.java,62,96
//,3
public class xxx {
    @Test
    public void testProducerInsertsIntoDatabaseThenConsumerFiresMessageExchange() throws Exception {
        // lets produce some objects
        template.send(endpoint, new Processor() {
            public void process(Exchange exchange) {
                exchange.getIn().setBody(new SendEmail("foo@bar.com"));
            }
        });

        // now lets assert that there is a result
        List<?> results = entityManager.createQuery(queryText).getResultList();
        assertEquals(1, results.size(), "Should have results: " + results);
        SendEmail mail = (SendEmail) results.get(0);
        assertEquals("foo@bar.com", mail.getAddress(), "address property");

        // now lets create a consumer to consume it
        consumer = endpoint.createConsumer(new Processor() {
            public void process(Exchange e) {
                LOG.info("Received exchange: " + e.getIn());
                receivedExchange = e;
                // should have a EntityManager
                EntityManager entityManager = e.getIn().getHeader(JpaConstants.ENTITY_MANAGER, EntityManager.class);
                assertNotNull(entityManager, "Should have a EntityManager as header");
                latch.countDown();
            }
        });
        consumer.start();

        assertTrue(latch.await(50, TimeUnit.SECONDS));

        assertNotNull(receivedExchange);
        SendEmail result = receivedExchange.getIn().getBody(SendEmail.class);
        assertNotNull(result, "Received a POJO");
        assertEquals("foo@bar.com", result.getAddress(), "address property");
    }

};