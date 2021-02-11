//,temp,sample_6434.java,2,9,temp,sample_9365.java,2,14
//,3
public class xxx {
public List<String> getGroups(String user) throws IOException {
String[] groups = new String[0];
try {
groups = getGroupsForUser(user);
} catch (Exception e) {
if (LOG.isDebugEnabled()) {
} else {


log.info("error getting groups for");
}
}
}

};