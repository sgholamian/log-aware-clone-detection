//,temp,sample_3316.java,2,14,temp,sample_6956.java,2,14
//,2
public class xxx {
public void finalizeStop(VirtualMachineProfile profile, Answer answer) {
IPAddressVO ip = _ipAddressDao.findByAssociatedVmId(profile.getId());
if (ip != null && ip.getSystem()) {
CallContext ctx = CallContext.current();
try {
_rulesMgr.disableStaticNat(ip.getId(), ctx.getCallingAccount(), ctx.getCallingUserId(), true);
} catch (Exception ex) {


log.info("failed to disable static nat and release system ip as a part of vm stop due to exception");
}
}
}

};