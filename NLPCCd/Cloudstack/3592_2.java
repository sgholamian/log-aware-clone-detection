//,temp,sample_2420.java,2,8,temp,sample_2398.java,2,8
//,2
public class xxx {
public Boolean fenceOff(VirtualMachine vm, Host host) {
if (host.getHypervisorType() != HypervisorType.Simulator) {


log.info("don t know how to fence non simulator hosts");
}
}

};