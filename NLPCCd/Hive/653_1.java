//,temp,sample_3918.java,2,15,temp,sample_3917.java,2,13
//,3
public class xxx {
public void run() {
while (statement.hasMoreLogs()) {
try {
incrementalLogs.addAll(statement.getQueryLog());
Thread.sleep(500);
} catch (SQLException e) {
fail("error in getting log thread");
} catch (InterruptedException e) {


log.info("getting log thread is interrupted error message");
}
}
}

};