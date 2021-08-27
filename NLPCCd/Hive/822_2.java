//,temp,sample_3665.java,2,17,temp,sample_3651.java,2,17
//,3
public class xxx {
public void dummy_method(){
Connection dbConn = null;
Statement stmt = null;
try {
dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
long latestValidStart = getDbTime(dbConn) - timeout;
stmt = dbConn.createStatement();
String s = "update COMPACTION_QUEUE set cq_worker_id = null, cq_start = null, cq_state = '" + INITIATED_STATE+ "' where cq_state = '" + WORKING_STATE + "' and cq_start < " +  latestValidStart;
stmt.executeUpdate(s);
dbConn.commit();
} catch (SQLException e) {


log.info("going to rollback");
}
}

};