//,temp,JpaWithNamedQueryAndParametersTest.java,64,113,temp,JpaTest.java,62,96
//,3
public class xxx {
    @Test
    public void testProducerInsertsIntoDatabaseThenConsumerFiresMessageExchange() throws Exception {
        transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                entityManager.joinTransaction();
                // lets delete any exiting records before the test
                entityManager.createQuery("delete from " + entityName).executeUpdate();
                // now lets create a dummy entry
                Customer dummy = new Customer();
                dummy.setName("Test");
                entityManager.persist(dummy);
                return null;
            }
        });

        List<?> results = entityManager.createQuery(queryText).getResultList();
        assertEquals(0, results.size(), "Should have no results: " + results);

        // lets produce some objects
        template.send("jpa://" + Customer.class.getName(), new Processor() {
            public void process(Exchange exchange) {
                Customer customer = new Customer();
                customer.setName("Willem");
                exchange.getIn().setBody(customer);
            }
        });

        // now lets assert that there is a result
        results = entityManager.createQuery(queryText).getResultList();
        assertEquals(1, results.size(), "Should have results: " + results);
        Customer customer = (Customer) results.get(0);
        assertEquals("Willem", customer.getName(), "name property");

        // now lets create a consumer to consume it
        consumer = endpoint.createConsumer(new Processor() {
            public void process(Exchange e) {
                LOG.info("Received exchange: " + e.getIn());
                receivedExchange = e;
                latch.countDown();
            }
        });
        consumer.start();

        assertTrue(latch.await(10, TimeUnit.SECONDS));

        assertReceivedResult(receivedExchange);

        JpaConsumer jpaConsumer = (JpaConsumer) consumer;
        assertURIQueryOption(jpaConsumer);
    }

};