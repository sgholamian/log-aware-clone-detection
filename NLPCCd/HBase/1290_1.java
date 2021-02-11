//,temp,sample_4563.java,2,14,temp,sample_4564.java,2,15
//,3
public class xxx {
public boolean waitForActiveAndReadyMaster(long timeout) throws IOException {
long start = System.currentTimeMillis();
while (System.currentTimeMillis() - start < timeout) {
try {
getMasterAdminService();
return true;
} catch (MasterNotRunningException m) {


log.info("master not started yet");
}
}
}

};