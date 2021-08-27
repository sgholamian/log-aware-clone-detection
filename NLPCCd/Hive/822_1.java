//,temp,sample_3665.java,2,17,temp,sample_3651.java,2,17
//,3
public class xxx {
public void dummy_method(){
Connection dbConn = null;
Statement stmt = null;
try {
dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
stmt = dbConn.createStatement();
String s = "update COMPACTION_QUEUE set CQ_HADOOP_JOB_ID = " + quoteString(hadoopJobId) + " WHERE CQ_ID = " + id;
int updateCount = stmt.executeUpdate(s);
closeStmt(stmt);
dbConn.commit();
} catch (SQLException e) {


log.info("sethadoopjobid");
}
}

};