//,temp,ConfigTest.java,323,349,temp,ConfigTest.java,94,125
//,3
public class xxx {
    @Test
    public void testMemoryConfig() throws Exception {
        File journalFile = new File(JOURNAL_ROOT + "testMemoryConfig");
        recursiveDelete(journalFile);

        File derbyFile = new File(DERBY_ROOT + "testMemoryConfig");
        recursiveDelete(derbyFile);

        BrokerService broker;
        broker = createBroker(new FileSystemResource(CONF_ROOT + "memory-example.xml"));

        try {
            assertEquals("Broker Config Error (brokerName)", "brokerMemoryConfigTest", broker.getBrokerName());

            PersistenceAdapter adapter = broker.getPersistenceAdapter();

            assertTrue("Should have created a memory persistence adapter", adapter instanceof MemoryPersistenceAdapter);
            assertTrue("Should have not created a derby directory at " + derbyFile.getAbsolutePath(), !derbyFile.exists());
            assertTrue("Should have not created a journal directory at " + journalFile.getAbsolutePath(), !journalFile.exists());

            LOG.info("Success");
        } finally {
            if (broker != null) {
                broker.stop();
            }
        }
    }

};