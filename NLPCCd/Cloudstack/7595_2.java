//,temp,sample_8367.java,2,20,temp,sample_8365.java,2,20
//,2
public class xxx {
public void dummy_method(){
for (HostPodVO pod : pods) {
DedicatedResourceVO dPod = _dedicatedDao.findByPodId(pod.getId());
if (dPod != null) {
if (!(childDomainIds.contains(dPod.getDomainId()))) {
throw new CloudRuntimeException("Pod " + pod.getName() + " under this Zone " + dc.getName() + " is dedicated to different account/domain");
}
if (accountId != null) {
if (dPod.getAccountId().equals(accountId)) {
podsToRelease.add(dPod);
} else {


log.info("pod under this zone is dedicated to different account domain");
}
}
}
}
}

};