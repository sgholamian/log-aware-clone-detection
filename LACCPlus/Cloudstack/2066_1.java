//,temp,FirstFitAllocator.java,190,250,temp,FirstFitAllocator.java,107,188
//,3
public class xxx {
    @Override
    public List<Host> allocateTo(VirtualMachineProfile vmProfile, DeploymentPlan plan, Type type, ExcludeList avoid, List<? extends Host> hosts, int returnUpTo,
        boolean considerReservedCapacity) {
        long dcId = plan.getDataCenterId();
        Long podId = plan.getPodId();
        Long clusterId = plan.getClusterId();
        ServiceOffering offering = vmProfile.getServiceOffering();
        VMTemplateVO template = (VMTemplateVO)vmProfile.getTemplate();
        Account account = vmProfile.getOwner();
        List<Host> suitableHosts = new ArrayList<Host>();
        List<Host> hostsCopy = new ArrayList<Host>(hosts);

        if (type == Host.Type.Storage) {
            // FirstFitAllocator should be used for user VMs only since it won't care whether the host is capable of
            // routing or not.
            return suitableHosts;
        }

        String hostTagOnOffering = offering.getHostTag();
        String hostTagOnTemplate = template.getTemplateTag();
        boolean hasSvcOfferingTag = hostTagOnOffering != null ? true : false;
        boolean hasTemplateTag = hostTagOnTemplate != null ? true : false;

        String haVmTag = (String)vmProfile.getParameter(VirtualMachineProfile.Param.HaTag);
        if (haVmTag != null) {
            hostsCopy.retainAll(_hostDao.listByHostTag(type, clusterId, podId, dcId, haVmTag));
        } else {
            if (hostTagOnOffering == null && hostTagOnTemplate == null) {
                hostsCopy.retainAll(_resourceMgr.listAllUpAndEnabledNonHAHosts(type, clusterId, podId, dcId));
            } else {
                if (hasSvcOfferingTag) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Looking for hosts having tag specified on SvcOffering:" + hostTagOnOffering);
                    }
                    hostsCopy.retainAll(_hostDao.listByHostTag(type, clusterId, podId, dcId, hostTagOnOffering));

                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Hosts with tag '" + hostTagOnOffering + "' are:" + hostsCopy);
                    }
                }

                if (hasTemplateTag) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Looking for hosts having tag specified on Template:" + hostTagOnTemplate);
                    }

                    hostsCopy.retainAll(_hostDao.listByHostTag(type, clusterId, podId, dcId, hostTagOnTemplate));

                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Hosts with tag '" + hostTagOnTemplate + "' are:" + hostsCopy);
                    }
                }
            }
        }

        if (!hostsCopy.isEmpty()) {
            suitableHosts = allocateTo(plan, offering, template, avoid, hostsCopy, returnUpTo, considerReservedCapacity, account);
        }

        return suitableHosts;
    }

};