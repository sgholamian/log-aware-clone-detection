//,temp,ConfigTest.java,323,349,temp,ConfigTest.java,94,125
//,3
public class xxx {
    @Test
    public void testJournaledJDBCConfig() throws Exception {

        File journalFile = new File(JOURNAL_ROOT + "testJournaledJDBCConfig/journal");
        recursiveDelete(journalFile);

        File derbyFile = new File(DERBY_ROOT + "testJournaledJDBCConfig/derbydb"); // Default
        recursiveDelete(derbyFile);

        BrokerService broker;
        broker = createBroker(new FileSystemResource(CONF_ROOT + "journaledjdbc-example.xml"));
        try {
            assertEquals("Broker Config Error (brokerName)", "brokerJournaledJDBCConfigTest", broker.getBrokerName());

            PersistenceAdapter adapter = broker.getPersistenceAdapter();

            assertTrue("Should have created a journal persistence adapter", adapter instanceof JournalPersistenceAdapter);
            assertTrue("Should have created a derby directory at " + derbyFile.getAbsolutePath(), derbyFile.exists());
            assertTrue("Should have created a journal directory at " + journalFile.getAbsolutePath(), journalFile.exists());

            // Check persistence factory configurations
            broker.getPersistenceAdapter();

            assertTrue(broker.getSystemUsage().getStoreUsage().getStore() instanceof JournalPersistenceAdapter);

            LOG.info("Success");
        } finally {
            if (broker != null) {
                broker.stop();
            }
        }
    }

};