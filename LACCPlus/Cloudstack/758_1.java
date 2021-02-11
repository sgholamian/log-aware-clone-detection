//,temp,SecondaryStorageManagerImpl.java,1296,1309,temp,ConsoleProxyManagerImpl.java,1524,1537
//,2
public class xxx {
    @Override
    public void finalizeStop(VirtualMachineProfile profile, Answer answer) {
        //release elastic IP here
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