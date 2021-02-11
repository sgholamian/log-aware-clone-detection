//,temp,sample_744.java,2,13,temp,sample_4239.java,2,13
//,3
public class xxx {
public static VmwareContext getContext(String vCenterAddress, String vCenterUserName, String vCenterPassword) throws Exception {
VmwareContext context = s_pool.getContext(vCenterAddress, vCenterUserName);
if (context == null) {
context = create(vCenterAddress, vCenterUserName, vCenterPassword);
} else {
if (!context.validate() || (context.getVimClient().getVcenterSessionTimeout() != s_vmwareMgr.getVcenterSessionTimeout())) {


log.info("validation of the context failed dispose and create a new one");
}
}
}

};