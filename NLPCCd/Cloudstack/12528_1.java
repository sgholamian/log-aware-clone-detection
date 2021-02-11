//,temp,sample_7200.java,2,8,temp,sample_2069.java,2,10
//,3
public class xxx {
public Boolean fenceOff(VirtualMachine vm, Host host) {
if (host.getHypervisorType() != HypervisorType.Ovm) {


log.info("don t know how to fence non ovm hosts");
}
}

};