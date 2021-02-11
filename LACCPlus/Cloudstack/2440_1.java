//,temp,Ovm3VmSupport.java,241,252,temp,OvmResourceBase.java,878,886
//,3
public class xxx {
    public GetVncPortAnswer execute(GetVncPortCommand cmd) {
        try {
            Xen host = new Xen(c);
            Xen.Vm vm = host.getRunningVmConfig(cmd.getName());
            Integer vncPort = vm.getVncPort();
            LOGGER.debug("get vnc port for " + cmd.getName() + ": " + vncPort);
            return new GetVncPortAnswer(cmd, c.getIp(), vncPort);
        } catch (Ovm3ResourceException e) {
            LOGGER.debug("get vnc port for " + cmd.getName() + " failed", e);
            return new GetVncPortAnswer(cmd, e.getMessage());
        }
    }

};