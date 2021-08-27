//,temp,sample_1236.java,2,18,temp,sample_1235.java,2,15
//,3
public class xxx {
private static boolean dropTable(Statement stmt, String name, int retryCount) throws SQLException {
for (int i = 0; i < 3; i++) {
try {
stmt.execute("DROP TABLE " + name);
return true;
} catch (SQLException e) {
if ("42Y55".equals(e.getSQLState()) && 30000 == e.getErrorCode()) {
return true;
}
if ("X0Y25".equals(e.getSQLState()) && 30000 == e.getErrorCode()) {


log.info("intermittent drop failure retrying try number");
}
}
}
}

};