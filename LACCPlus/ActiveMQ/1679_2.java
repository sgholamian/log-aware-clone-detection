//,temp,JDBCCommitExceptionTest.java,79,96,temp,XACompletionTest.java,1146,1173
//,3
public class xxx {
    protected void dumpMessages() throws Exception {

        if (persistenceAdapterChoice.compareTo(PersistenceAdapterChoice.JDBC) != 0) {
            return;
        }
        WireFormat wireFormat = new OpenWireFormat();
        java.sql.Connection conn = ((JDBCPersistenceAdapter) broker.getPersistenceAdapter()).getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT ID, MSG, XID FROM ACTIVEMQ_MSGS");
        ResultSet result = statement.executeQuery();
        LOG.info("Messages in broker db...");
        while (result.next()) {
            long id = result.getLong(1);
            org.apache.activemq.command.Message message = (org.apache.activemq.command.Message) wireFormat.unmarshal(new ByteSequence(result.getBytes(2)));
            String xid = result.getString(3);
            LOG.info("id: " + id + ", message SeqId: " + message.getMessageId().getBrokerSequenceId() + ", XID:" + xid + ", MSG: " + message);
        }
        statement.close();

        statement = conn.prepareStatement("SELECT LAST_ACKED_ID, CLIENT_ID, SUB_NAME, PRIORITY, XID FROM ACTIVEMQ_ACKS");
        result = statement.executeQuery();
        LOG.info("Messages in ACKS table db...");
        while (result.next()) {
            LOG.info("lastAcked: {}, clientId: {}, SUB_NAME: {}, PRIORITY: {}, XID {}",
                    result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getString(5));
        }
        statement.close();
        conn.close();
    }

};