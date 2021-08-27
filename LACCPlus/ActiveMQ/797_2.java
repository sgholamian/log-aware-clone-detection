//,temp,ConfigTest.java,297,318,temp,JDBCConfigTest.java,47,73
//,3
public class xxx {
    @Test
    public void testJdbcConfig() throws Exception {
        File journalFile = new File(JOURNAL_ROOT + "testJDBCConfig/journal");
        recursiveDelete(journalFile);

        File derbyFile = new File(DERBY_ROOT + "testJDBCConfig/derbydb"); // Default
        recursiveDelete(derbyFile);

        BrokerService broker;
        broker = createBroker(new FileSystemResource(CONF_ROOT + "jdbc-example.xml"));
        try {
            assertEquals("Broker Config Error (brokerName)", "brokerJdbcConfigTest", broker.getBrokerName());

            PersistenceAdapter adapter = broker.getPersistenceAdapter();

            assertTrue("Should have created a jdbc persistence adapter", adapter instanceof JDBCPersistenceAdapter);
            assertEquals("JDBC Adapter Config Error (cleanupPeriod)", 60000, ((JDBCPersistenceAdapter) adapter).getCleanupPeriod());
            assertTrue("Should have created an EmbeddedDataSource", ((JDBCPersistenceAdapter) adapter).getDataSource() instanceof EmbeddedDataSource);
            assertTrue("Should have created a DefaultWireFormat", ((JDBCPersistenceAdapter) adapter).getWireFormat() instanceof ObjectStreamWireFormat);

            LOG.info("Success");
        } finally {
            if (broker != null) {
                broker.stop();
            }
        }
    }

};