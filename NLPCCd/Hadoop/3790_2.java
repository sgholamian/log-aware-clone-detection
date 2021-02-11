//,temp,sample_889.java,2,10,temp,sample_9364.java,2,13
//,3
public class xxx {
public List<String> getGroups(String user) throws IOException {
String[] groups = new String[0];
try {
groups = getGroupsForUser(user);
} catch (Exception e) {
if (LOG.isDebugEnabled()) {


log.info("error getting groups for");
}
}
}

};