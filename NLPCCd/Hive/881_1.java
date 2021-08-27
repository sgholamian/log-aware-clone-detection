//,temp,sample_3221.java,2,17,temp,sample_4943.java,2,12
//,3
public class xxx {
public static byte[] serializeJobConf(JobConf jobConf) {
ByteArrayOutputStream out = new ByteArrayOutputStream();
try {
jobConf.write(new DataOutputStream(out));
} catch (IOException e) {
return null;
} finally {
try {
out.close();
} catch (IOException e) {


log.info("error closing output stream");
}
}
}

};