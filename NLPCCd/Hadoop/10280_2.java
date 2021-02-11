//,temp,sample_8832.java,2,19,temp,sample_8842.java,2,18
//,3
public class xxx {
public void dummy_method(){
String planCreatePath = getNodePath(reservationRoot, planName);
String reservationPath = getNodePath(planCreatePath, reservationIdName);
byte[] reservationData = reservationAllocation.toByteArray();
if (!exists(planCreatePath)) {
if (LOG.isDebugEnabled()) {
}
trx.create(planCreatePath, null, zkAcl, CreateMode.PERSISTENT);
}
if (isUpdate) {
if (LOG.isDebugEnabled()) {


log.info("updating reservation in plan at");
}
}
}

};