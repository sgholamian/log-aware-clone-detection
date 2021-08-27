//,temp,sample_128.java,2,11,temp,sample_129.java,2,14
//,3
public class xxx {
private void publishJiraComment(boolean error, List<String> messages, SortedSet<String> failedTests, Set<String> addedTests) {
if(mConfiguration.getJiraName().isEmpty()) {
return;
}
if(mConfiguration.getJiraUrl().isEmpty()) {
return;
}
if(mConfiguration.getJiraUser().isEmpty()) {


log.info("skipping jira comment as user is empty");
}
}

};