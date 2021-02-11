//,temp,sample_4207.java,2,18,temp,sample_4206.java,2,17
//,3
public class xxx {
public boolean hasAccess(Type type, UserGroupInformation ugi) {
boolean access = acls.get(type).isUserAllowed(ugi);
if (LOG.isDebugEnabled()) {
}
if (access) {
AccessControlList blacklist = blacklistedAcls.get(type);
access = (blacklist == null) || !blacklist.isUserInList(ugi);
if (LOG.isDebugEnabled()) {
if (blacklist == null) {


log.info("no blacklist for");
}
}
}
}

};