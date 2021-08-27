//,temp,sample_3856.java,2,16,temp,sample_7328.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (ObjectHelper.isNotEmpty(knownHostsFile)) {
jsch.setKnownHosts(knownHostsFile);
}
final Session session = jsch.getSession(configuration.getUsername(), configuration.getHost(), configuration.getPort());
if (isNotEmpty(sftpConfig.getStrictHostKeyChecking())) {
session.setConfig("StrictHostKeyChecking", sftpConfig.getStrictHostKeyChecking());
}
session.setServerAliveInterval(sftpConfig.getServerAliveInterval());
session.setServerAliveCountMax(sftpConfig.getServerAliveCountMax());
if (sftpConfig.getCompression() > 0) {


log.info("using compression");
}
}

};