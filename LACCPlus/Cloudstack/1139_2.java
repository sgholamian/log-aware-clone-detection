//,temp,VmwareContextFactory.java,84,106,temp,VmwareContextFactory.java,58,82
//,3
public class xxx {
    public static VmwareContext create(String vCenterAddress, String vCenterUserName, String vCenterPassword) throws Exception {
        assert (vCenterAddress != null);
        assert (vCenterUserName != null);
        assert (vCenterPassword != null);

        String serviceUrl = "https://" + vCenterAddress + "/sdk/vimService";
        if (s_logger.isDebugEnabled())
            s_logger.debug("initialize VmwareContext. url: " + serviceUrl + ", username: " + vCenterUserName + ", password: " +
                StringUtils.getMaskedPasswordForDisplay(vCenterPassword));

        VmwareClient vimClient = new VmwareClient(vCenterAddress + "-" + s_seq++);
        vimClient.setVcenterSessionTimeout(s_vmwareMgr.getVcenterSessionTimeout());
        vimClient.connect(serviceUrl, vCenterUserName, vCenterPassword);

        VmwareContext context = new VmwareContext(vimClient, vCenterAddress);
        context.registerStockObject(VmwareManager.CONTEXT_STOCK_NAME, s_vmwareMgr);

        context.registerStockObject("serviceconsole", s_vmwareMgr.getServiceConsolePortGroupName());
        context.registerStockObject("manageportgroup", s_vmwareMgr.getManagementPortGroupName());
        context.registerStockObject("noderuninfo", String.format("%d-%d", s_clusterMgr.getManagementNodeId(), s_clusterMgr.getCurrentRunId()));

        context.setPoolInfo(s_pool, VmwareContextPool.composePoolKey(vCenterAddress, vCenterUserName));

        return context;
    }

};