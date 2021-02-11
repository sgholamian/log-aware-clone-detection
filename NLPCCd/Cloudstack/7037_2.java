//,temp,sample_9447.java,2,18,temp,sample_9455.java,2,18
//,3
public class xxx {
public void dummy_method(){
boolean result = element.saveSSHKey(defaultNetwork, defaultNicProfile, vmProfile, sshPublicKey);
if (!result) {
return false;
} else {
if (vmInstance.getState() == State.Stopped) {
return true;
}
if (rebootVirtualMachine(userId, vmId) == null) {
return false;
} else {


log.info("vm is rebooted successfully as a part of ssh key reset");
}
}
}

};