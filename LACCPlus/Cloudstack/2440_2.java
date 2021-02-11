//,temp,Ovm3VmSupport.java,241,252,temp,OvmResourceBase.java,878,886
//,3
public class xxx {
    protected GetVncPortAnswer execute(GetVncPortCommand cmd) {
        try {
            Integer vncPort = OvmVm.getVncPort(_conn, cmd.getName());
            return new GetVncPortAnswer(cmd, _ip, vncPort);
        } catch (Exception e) {
            s_logger.debug("get vnc port for " + cmd.getName() + " failed", e);
            return new GetVncPortAnswer(cmd, e.getMessage());
        }
    }

};