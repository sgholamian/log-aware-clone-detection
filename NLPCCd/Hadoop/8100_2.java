//,temp,sample_7391.java,2,11,temp,sample_3854.java,2,11
//,3
public class xxx {
public boolean checkAccess(UserGroupInformation callerUGI, QueueACL acl, String queueName) {
CSQueue queue = getQueue(queueName);
if (queue == null) {
if (LOG.isDebugEnabled()) {


log.info("acl not found for queue access type for queue");
}
}
}

};