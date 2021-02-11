//,temp,sample_6506.java,2,12,temp,sample_8407.java,2,11
//,3
public class xxx {
private UserGroupInformation checkAcls(String method) throws IOException {
UserGroupInformation user;
try {
user = UserGroupInformation.getCurrentUser();
} catch (IOException ioe) {


log.info("couldn t get current user");
}
}

};