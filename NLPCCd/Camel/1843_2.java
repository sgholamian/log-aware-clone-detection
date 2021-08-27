//,temp,sample_2698.java,2,11,temp,sample_3564.java,2,11
//,2
public class xxx {
private String createCandidateName() {
StringBuilder builder = new StringBuilder();
try {
builder.append(InetAddress.getLocalHost().getCanonicalHostName());
} catch (UnknownHostException ex) {


log.info("failed to get the local hostname");
}
}

};