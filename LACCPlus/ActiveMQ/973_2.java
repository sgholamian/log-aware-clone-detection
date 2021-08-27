//,temp,JDBCTablePrefixAssignedTest.java,94,110,temp,JDBCStoreOrderTest.java,37,50
//,3
public class xxx {
    @Override
     protected void dumpMessages() throws Exception {
        WireFormat wireFormat = new OpenWireFormat();
        java.sql.Connection conn = ((JDBCPersistenceAdapter) broker.getPersistenceAdapter()).getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT ID, MSG FROM ACTIVEMQ_MSGS");    
        ResultSet result = statement.executeQuery();
        while(result.next()) {
            long id = result.getLong(1);
            Message message = (Message)wireFormat.unmarshal(new ByteSequence(result.getBytes(2)));
            LOG.info("id: " + id + ", message SeqId: " + message.getMessageId().getBrokerSequenceId() + ", MSG: " + message);
        }
        statement.close();
        conn.close();
    }

};