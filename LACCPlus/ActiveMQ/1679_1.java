//,temp,JDBCCommitExceptionTest.java,79,96,temp,XACompletionTest.java,1146,1173
//,3
public class xxx {
     protected int dumpMessages() throws Exception {
        int count = 0;
        WireFormat wireFormat = new OpenWireFormat();
        java.sql.Connection conn = ((JDBCPersistenceAdapter) broker.getPersistenceAdapter()).getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT ID, XID, MSG FROM ACTIVEMQ_MSGS");
        ResultSet result = statement.executeQuery();
        LOG.info("Messages left in broker after test");
        while(result.next()) {
            long id = result.getLong(1);
            String xid = result.getString(2);
            org.apache.activemq.command.Message message = (org.apache.activemq.command.Message)wireFormat.unmarshal(new ByteSequence(result.getBytes(3)));
            LOG.info("id: " + id + ", xid: " + xid + ", message SeqId: " + message.getMessageId().getBrokerSequenceId() + ", MSG: " + message);
            count++;
        }
        statement.close();
        conn.close();
        return count;
    }

};