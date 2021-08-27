//,temp,DestinationWildcardTest.java,117,152,temp,DestinationWildcardTest.java,69,110
//,3
public class xxx {
    @Test
    public void testDestinationWildcardTwoEntries() throws Exception {
        LOG.info("testDestinationWildcard() called.");

        // configure broker for policyEntries
        List<PolicyEntry> entries = new ArrayList<PolicyEntry>();

        PolicyEntry e1 = new PolicyEntry();
        e1.setDestination(new ActiveMQQueue("DomainA.DomainB.*.*.Prioritised.Queue"));
        e1.setMemoryLimit(QUEUE_LIMIT);
        e1.setPrioritizedMessages(true);
        entries.add(e1);

        PolicyEntry e2 = new PolicyEntry();
        e2.setDestination(new ActiveMQQueue("DomainA.DomainB.>"));
        e2.setMemoryLimit(3000000);
        e2.setPrioritizedMessages(false);
        entries.add(e2);

        PolicyMap policyMap = new PolicyMap();
        policyMap.setPolicyEntries(entries);
        broker.setDestinationPolicy(policyMap);
        broker.start();
        broker.waitUntilStarted();

        // verify broker isn't null
        Assert.assertNotNull(broker);

        // verify effective policy is in place.
        ManagedRegionBroker rb = (ManagedRegionBroker) broker.getRegionBroker();
        org.apache.activemq.broker.region.Queue queue = (Queue) rb.addDestination(new ConnectionContext(), new ActiveMQQueue(DESTNAME), true);
        Assert.assertTrue("PolicyEntry should have priorityMessages enabled for destination " + DESTNAME, queue.isPrioritizedMessages());
        long limit = queue.getMemoryUsage().getLimit();
        LOG.info("MemoryLimit of {}: expected: 5242880, actual: {}", DESTNAME, limit);
        Assert.assertEquals("Memory limit is expected to be " + QUEUE_LIMIT + " for this destination, but does not match.", QUEUE_LIMIT, limit);
    }

};