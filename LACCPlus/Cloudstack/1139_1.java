//,temp,VmwareContextFactory.java,84,106,temp,VmwareContextFactory.java,58,82
//,3
public class xxx {
    public static VmwareContext getContext(String vCenterAddress, String vCenterUserName, String vCenterPassword) throws Exception {
        VmwareContext context = s_pool.getContext(vCenterAddress, vCenterUserName);
        if (context == null) {
            context = create(vCenterAddress, vCenterUserName, vCenterPassword);
        } else {
            // Validate current context and verify if vCenter session timeout value of the context matches the timeout value set by Admin
            if (!context.validate() || (context.getVimClient().getVcenterSessionTimeout() != s_vmwareMgr.getVcenterSessionTimeout())) {
                s_logger.info("Validation of the context failed, dispose and create a new one");
                context.close();
                context = create(vCenterAddress, vCenterUserName, vCenterPassword);
            }
        }

        if (context != null) {
            context.registerStockObject(VmwareManager.CONTEXT_STOCK_NAME, s_vmwareMgr);

            context.registerStockObject("serviceconsole", s_vmwareMgr.getServiceConsolePortGroupName());
            context.registerStockObject("manageportgroup", s_vmwareMgr.getManagementPortGroupName());
            context.registerStockObject("noderuninfo", String.format("%d-%d", s_clusterMgr.getManagementNodeId(), s_clusterMgr.getCurrentRunId()));
        }

        return context;
    }

};