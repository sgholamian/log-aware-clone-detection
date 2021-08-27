//,temp,JmsJdbcXATest.java,52,75,temp,JmsJdbcXARollbackTest.java,52,75
//,2
public class xxx {
    public java.sql.Connection initDb() throws Exception {
        String createStatement =
                "CREATE TABLE SCP_INPUT_MESSAGES (" +
                        "id int NOT NULL GENERATED ALWAYS AS IDENTITY, " +
                        "messageId varchar(96) NOT NULL, " +
                        "messageCorrelationId varchar(96) NOT NULL, " +
                        "messageContent varchar(2048) NOT NULL, " +
                        "PRIMARY KEY (id) )";

        java.sql.Connection conn = getJDBCConnection();
        try {
            conn.createStatement().execute(createStatement);
        } catch (SQLException alreadyExists) {
            log.info("ex on create tables", alreadyExists);
        }

        try {
            conn.createStatement().execute("DELETE FROM SCP_INPUT_MESSAGES");
        } catch (SQLException ex) {
            log.info("ex on create delete all", ex);
        }

        return conn;
    }

};