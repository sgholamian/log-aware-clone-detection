//,temp,JmsJdbcXATest.java,82,93,temp,JmsJdbcXARollbackTest.java,82,93
//,2
public class xxx {
    private int dumpDb(java.sql.Connection jdbcConn) throws Exception {
        int count = 0;
        ResultSet resultSet = jdbcConn.createStatement().executeQuery("SELECT * FROM SCP_INPUT_MESSAGES");
        while (resultSet.next()) {
            count++;
            log.info("message - seq:" + resultSet.getInt(1)
                    + ", id: " + resultSet.getString(2)
                    + ", corr: " + resultSet.getString(3)
                    + ", content: " + resultSet.getString(4));
        }
        return count;
    }

};