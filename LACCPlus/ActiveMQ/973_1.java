//,temp,JDBCTablePrefixAssignedTest.java,94,110,temp,JDBCStoreOrderTest.java,37,50
//,3
public class xxx {
    protected List<Message> dumpMessages() throws Exception {
        WireFormat wireFormat = new OpenWireFormat();
        java.sql.Connection conn = ((JDBCPersistenceAdapter) service.getPersistenceAdapter()).getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT ID, MSG FROM MYPREFIX_ACTIVEMQ_MSGS");
        ResultSet result = statement.executeQuery();
        ArrayList<Message> results = new ArrayList<Message>();
        while(result.next()) {
            long id = result.getLong(1);
            Message message = (Message)wireFormat.unmarshal(new ByteSequence(result.getBytes(2)));
            LOG.info("id: " + id + ", message SeqId: " + message.getMessageId().getBrokerSequenceId() + ", MSG: " + message);
            results.add(message);
        }
        statement.close();
        conn.close();

        return results;
    }

};