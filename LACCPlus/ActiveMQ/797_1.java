//,temp,ConfigTest.java,297,318,temp,JDBCConfigTest.java,47,73
//,3
public class xxx {
    @Test
    public void testJournalConfig() throws Exception {
        File journalFile = new File(JOURNAL_ROOT + "testJournalConfig/journal");
        recursiveDelete(journalFile);

        BrokerService broker;
        broker = createBroker(new FileSystemResource(CONF_ROOT + "journal-example.xml"));
        try {
            assertEquals("Broker Config Error (brokerName)", "brokerJournalConfigTest", broker.getBrokerName());

            PersistenceAdapter adapter = broker.getPersistenceAdapter();

            assertTrue("Should have created a journal persistence adapter", adapter instanceof JournalPersistenceAdapter);
            assertTrue("Should have created a journal directory at " + journalFile.getAbsolutePath(), journalFile.exists());

            LOG.info("Success");
        } finally {
            if (broker != null) {
                broker.stop();
            }
        }
    }

};