//,temp,sample_3313.java,2,18,temp,sample_6952.java,2,18
//,2
public class xxx {
public void dummy_method(){
NicProfile controlNic = null;
for (NicProfile nic : profile.getNics()) {
if (nic.getTrafficType() == TrafficType.Management) {
managementNic = nic;
} else if (nic.getTrafficType() == TrafficType.Control && nic.getIPv4Address() != null) {
controlNic = nic;
}
}
if (controlNic == null) {
if (managementNic == null) {


log.info("management network doesn t exist for the secondarystoragevm");
}
}
}

};