//,temp,sample_2068.java,2,8,temp,sample_2069.java,2,10
//,3
public class xxx {
public Boolean fenceOff(VirtualMachine vm, Host host) {
if (host.getHypervisorType() != HypervisorType.Ovm3) {


log.info("don t know how to fence non hosts");
}
}

};