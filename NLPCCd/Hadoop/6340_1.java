//,temp,sample_5034.java,2,11,temp,sample_6507.java,2,12
//,3
public class xxx {
private UserGroupInformation getCallerUgi(ApplicationId applicationId, String operation) throws YarnException {
UserGroupInformation callerUGI;
try {
callerUGI = UserGroupInformation.getCurrentUser();
} catch (IOException ie) {


log.info("error getting ugi");
}
}

};