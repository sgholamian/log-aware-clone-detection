//,temp,ConsoleProxyManagerImpl.java,1524,1537,temp,IpAddressManagerImpl.java,2004,2020
//,3
public class xxx {
    @Override
    public void finalizeStop(VirtualMachineProfile profile, Answer answer) {
        //release elastic IP here if assigned
        IPAddressVO ip = _ipAddressDao.findByAssociatedVmId(profile.getId());
        if (ip != null && ip.getSystem()) {
            CallContext ctx = CallContext.current();
            try {
                _rulesMgr.disableStaticNat(ip.getId(), ctx.getCallingAccount(), ctx.getCallingUserId(), true);
            } catch (Exception ex) {
                s_logger.warn("Failed to disable static nat and release system ip " + ip + " as a part of vm " + profile.getVirtualMachine() + " stop due to exception ",
                    ex);
            }
        }
    }

};