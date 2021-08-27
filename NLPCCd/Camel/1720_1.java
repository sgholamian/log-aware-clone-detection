//,temp,sample_3856.java,2,16,temp,sample_7328.java,2,17
//,3
public class xxx {
public void dummy_method(){
session = jsch.getSession(config.getUsername(), config.getHost(), config.getPort());
session.setTimeout(config.getTimeout());
session.setUserInfo(new SessionUserInfo(config));
if (ObjectHelper.isNotEmpty(config.getStrictHostKeyChecking())) {
session.setConfig("StrictHostKeyChecking", config.getStrictHostKeyChecking());
}
if (ObjectHelper.isNotEmpty(config.getPreferredAuthentications())) {
session.setConfig("PreferredAuthentications", config.getPreferredAuthentications());
}
int timeout = config.getConnectTimeout();


log.info("connecting to with timeout ms no");
}

};