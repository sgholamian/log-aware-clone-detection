//,temp,sample_6389.java,2,20,temp,sample_6387.java,2,20
//,2
public class xxx {
public void dummy_method(){
if (providers != null) {
for (DataStoreProvider provider : providers) {
if (provider instanceof PrimaryDataStoreProvider) {
try {
HypervisorHostListener hypervisorHostListener = provider.getHostListener();
if (hypervisorHostListener != null) {
hypervisorHostListener.hostAboutToBeRemoved(hostId);
}
}
catch (Exception ex) {


log.info("hostabouttoberemoved long failed for storage provider");
}
}
}
}
}

};