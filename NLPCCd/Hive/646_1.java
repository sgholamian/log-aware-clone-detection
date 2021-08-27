//,temp,sample_3493.java,2,12,temp,sample_5556.java,2,14
//,3
public class xxx {
protected void destruct() {
if (lockMgr != null) {
try {
lockMgr.close();
} catch (LockException e) {


log.info("got exception when closing lock manager");
}
}
}

};