//,temp,sample_4944.java,2,10,temp,sample_4840.java,2,10
//,3
public class xxx {
protected static void closeStmt(Statement stmt) {
try {
if (stmt != null && !stmt.isClosed()) stmt.close();
} catch (SQLException e) {


log.info("failed to close statement");
}
}

};