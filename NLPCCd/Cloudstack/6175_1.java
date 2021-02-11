//,temp,sample_6390.java,2,20,temp,sample_6387.java,2,20
//,3
public class xxx {
public void dummy_method(){
if (providers != null) {
for (DataStoreProvider provider : providers) {
if (provider instanceof PrimaryDataStoreProvider) {
try {
HypervisorHostListener hypervisorHostListener = provider.getHostListener();
if (hypervisorHostListener != null) {
hypervisorHostListener.hostRemoved(hostId, clusterId);
}
}
catch (Exception ex) {


log.info("hostremoved long long failed for storage provider");
}
}
}
}
}

};