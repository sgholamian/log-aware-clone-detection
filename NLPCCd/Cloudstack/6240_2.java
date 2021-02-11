//,temp,sample_5869.java,2,17,temp,sample_5874.java,2,17
//,3
public class xxx {
public void dummy_method(){
sreq.setSequence(892403718);
byte[] bytes = sreq.getBytes();
assert Request.getSequence(bytes) == 892403718;
assert Request.getManagementServerId(bytes) == 3;
assert Request.getAgentId(bytes) == 2;
assert Request.getViaAgentId(bytes) == 2;
Request creq = null;
try {
creq = Request.parse(bytes);
} catch (ClassNotFoundException e) {


log.info("unable to parse bytes");
}
}

};