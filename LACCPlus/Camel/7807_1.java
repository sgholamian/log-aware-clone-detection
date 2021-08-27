//,temp,JmsJdbcXARollbackTest.java,93,102,temp,JmsJdbcXATest.java,84,93
//,2
public class xxx {
    private int dumpDb(java.sql.Connection jdbcConn) throws Exception {
        int count = 0;
        ResultSet resultSet = jdbcConn.createStatement().executeQuery("SELECT * FROM SCP_INPUT_MESSAGES");
        while (resultSet.next()) {
            count++;
            LOG.info("message - seq: {}, id: {}, corr: {}, content: {}", resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4));
        }
        return count;
    }

};