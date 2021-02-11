//,temp,sample_8517.java,2,17,temp,sample_8509.java,2,17
//,3
public class xxx {
public void dummy_method(){
cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
cstmt.registerOutParameter(3, java.sql.Types.VARBINARY);
long startTime = clock.getTime();
cstmt.executeUpdate();
long stopTime = clock.getTime();
if (cstmt.getString(2) != null && cstmt.getBytes(3) != null) {
subClusterPolicyConfiguration = SubClusterPolicyConfiguration.newInstance(request.getQueue(), cstmt.getString(2), ByteBuffer.wrap(cstmt.getBytes(3)));
if (LOG.isDebugEnabled()) {
}
} else {


log.info("policy for queue does not exist");
}
}

};