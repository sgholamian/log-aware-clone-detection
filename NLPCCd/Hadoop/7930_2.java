//,temp,sample_2089.java,2,18,temp,sample_4208.java,2,19
//,3
public class xxx {
public void dummy_method(){
boolean access = acls.get(type).isUserAllowed(ugi);
if (LOG.isDebugEnabled()) {
}
if (access) {
AccessControlList blacklist = blacklistedAcls.get(type);
access = (blacklist == null) || !blacklist.isUserInList(ugi);
if (LOG.isDebugEnabled()) {
if (blacklist == null) {
} else if (access) {
} else {


log.info("user is not in");
}
}
}
}

};