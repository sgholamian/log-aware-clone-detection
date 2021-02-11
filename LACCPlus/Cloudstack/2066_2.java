//,temp,FirstFitAllocator.java,190,250,temp,FirstFitAllocator.java,107,188
//,3
public class xxx {
    @Override
    public List<Host> allocateTo(VirtualMachineProfile vmProfile, DeploymentPlan plan, Type type, ExcludeList avoid, int returnUpTo, boolean considerReservedCapacity) {

        long dcId = plan.getDataCenterId();
        Long podId = plan.getPodId();
        Long clusterId = plan.getClusterId();
        ServiceOffering offering = vmProfile.getServiceOffering();
        VMTemplateVO template = (VMTemplateVO)vmProfile.getTemplate();
        Account account = vmProfile.getOwner();

        if (type == Host.Type.Storage) {
            // FirstFitAllocator should be used for user VMs only since it won't care whether the host is capable of routing or not
            return new ArrayList<Host>();
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Looking for hosts in dc: " + dcId + "  pod:" + podId + "  cluster:" + clusterId);
        }

        String hostTagOnOffering = offering.getHostTag();
        String hostTagOnTemplate = template.getTemplateTag();

        boolean hasSvcOfferingTag = hostTagOnOffering != null ? true : false;
        boolean hasTemplateTag = hostTagOnTemplate != null ? true : false;

        List<HostVO> clusterHosts = new ArrayList<HostVO>();

        String haVmTag = (String)vmProfile.getParameter(VirtualMachineProfile.Param.HaTag);
        if (haVmTag != null) {
            clusterHosts = _hostDao.listByHostTag(type, clusterId, podId, dcId, haVmTag);
        } else {
            if (hostTagOnOffering == null && hostTagOnTemplate == null) {
                clusterHosts = _resourceMgr.listAllUpAndEnabledNonHAHosts(type, clusterId, podId, dcId);
            } else {
                List<HostVO> hostsMatchingOfferingTag = new ArrayList<HostVO>();
                List<HostVO> hostsMatchingTemplateTag = new ArrayList<HostVO>();
                if (hasSvcOfferingTag) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Looking for hosts having tag specified on SvcOffering:" + hostTagOnOffering);
                    }
                    hostsMatchingOfferingTag = _hostDao.listByHostTag(type, clusterId, podId, dcId, hostTagOnOffering);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Hosts with tag '" + hostTagOnOffering + "' are:" + hostsMatchingOfferingTag);
                    }
                }
                if (hasTemplateTag) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Looking for hosts having tag specified on Template:" + hostTagOnTemplate);
                    }
                    hostsMatchingTemplateTag = _hostDao.listByHostTag(type, clusterId, podId, dcId, hostTagOnTemplate);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Hosts with tag '" + hostTagOnTemplate + "' are:" + hostsMatchingTemplateTag);
                    }
                }

                if (hasSvcOfferingTag && hasTemplateTag) {
                    hostsMatchingOfferingTag.retainAll(hostsMatchingTemplateTag);
                    clusterHosts = _hostDao.listByHostTag(type, clusterId, podId, dcId, hostTagOnTemplate);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Found " + hostsMatchingOfferingTag.size() + " Hosts satisfying both tags, host ids are:" + hostsMatchingOfferingTag);
                    }

                    clusterHosts = hostsMatchingOfferingTag;
                } else {
                    if (hasSvcOfferingTag) {
                        clusterHosts = hostsMatchingOfferingTag;
                    } else {
                        clusterHosts = hostsMatchingTemplateTag;
                    }
                }
            }
        }

        // add all hosts that we are not considering to the avoid list
        List<HostVO> allhostsInCluster = _hostDao.listAllUpAndEnabledNonHAHosts(type, clusterId, podId, dcId, null);
        allhostsInCluster.removeAll(clusterHosts);
        for (HostVO host : allhostsInCluster) {
            avoid.addHost(host.getId());
        }

        return allocateTo(plan, offering, template, avoid, clusterHosts, returnUpTo, considerReservedCapacity, account);
    }

};