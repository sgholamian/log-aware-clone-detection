//,temp,sample_5875.java,2,17,temp,sample_5870.java,2,17
//,2
public class xxx {
public void dummy_method(){
byte[] bytes = sreq.getBytes();
assert Request.getSequence(bytes) == 892403717;
assert Request.getManagementServerId(bytes) == 3;
assert Request.getAgentId(bytes) == 2;
assert Request.getViaAgentId(bytes) == 2;
Request creq = null;
try {
creq = Request.parse(bytes);
} catch (ClassNotFoundException e) {
} catch (UnsupportedVersionException e) {


log.info("unable to parse bytes");
}
}

};