//,temp,sample_2048.java,2,11,temp,sample_8407.java,2,11
//,3
public class xxx {
public static UserGroupInformation verifyAdminAccess( YarnAuthorizationProvider authorizer, String method, String module, final Log LOG) throws IOException {
UserGroupInformation user;
try {
user = UserGroupInformation.getCurrentUser();
} catch (IOException ioe) {


log.info("couldn t get current user");
}
}

};