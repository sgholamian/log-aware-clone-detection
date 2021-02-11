//,temp,sample_8367.java,2,20,temp,sample_8373.java,2,20
//,3
public class xxx {
public void dummy_method(){
for (HostVO host : hosts) {
DedicatedResourceVO dHost = _dedicatedDao.findByHostId(host.getId());
if (dHost != null) {
if (!(getDomainChildIds(domainId).contains(dHost.getDomainId()))) {
throw new CloudRuntimeException("Host " + host.getName() + " under this Pod " + pod.getName() + " is dedicated to different account/domain");
}
if (accountId != null) {
if (dHost.getAccountId().equals(accountId)) {
hostsToRelease.add(dHost);
} else {


log.info("host under this pod is dedicated to different account domain");
}
}
}
}
}

};