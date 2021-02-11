//,temp,NetworkHelperImpl.java,450,540,temp,InternalLoadBalancerVMManagerImpl.java,724,821
//,3
public class xxx {
    protected DomainRouterVO deployInternalLbVm(final Account owner, final DeployDestination dest, final DeploymentPlan plan, final Map<Param, Object> params, final long internalLbProviderId,
            final long svcOffId, final Long vpcId, final LinkedHashMap<Network, List<? extends NicProfile>> networks, final boolean startVm) throws ConcurrentOperationException,
            InsufficientAddressCapacityException, InsufficientServerCapacityException, InsufficientCapacityException, StorageUnavailableException,
            ResourceUnavailableException {

        final ServiceOfferingVO routerOffering = _serviceOfferingDao.findById(svcOffId);

        // Internal lb is the network element, we don't know the hypervisor type yet.
        // Try to allocate the internal lb twice using diff hypervisors, and when failed both times, throw the exception up
        final List<HypervisorType> hypervisors = getHypervisors(dest, plan, null);

        int allocateRetry = 0;
        int startRetry = 0;
        DomainRouterVO internalLbVm = null;
        for (final Iterator<HypervisorType> iter = hypervisors.iterator(); iter.hasNext();) {
            final HypervisorType hType = iter.next();
            try {
                final long id = _internalLbVmDao.getNextInSequence(Long.class, "id");
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Creating the internal lb vm " + id + " in datacenter " + dest.getDataCenter() + " with hypervisor type " + hType);
                }
                String templateName = null;
                switch (hType) {
                case XenServer:
                    templateName = VirtualNetworkApplianceManager.RouterTemplateXen.valueIn(dest.getDataCenter().getId());
                    break;
                case KVM:
                    templateName = VirtualNetworkApplianceManager.RouterTemplateKvm.valueIn(dest.getDataCenter().getId());
                    break;
                case VMware:
                    templateName = VirtualNetworkApplianceManager.RouterTemplateVmware.valueIn(dest.getDataCenter().getId());
                    break;
                case Hyperv:
                    templateName = VirtualNetworkApplianceManager.RouterTemplateHyperV.valueIn(dest.getDataCenter().getId());
                    break;
                case LXC:
                    templateName = VirtualNetworkApplianceManager.RouterTemplateLxc.valueIn(dest.getDataCenter().getId());
                    break;
                default:
                    break;
                }
                final VMTemplateVO template = _templateDao.findRoutingTemplate(hType, templateName);

                if (template == null) {
                    s_logger.debug(hType + " won't support system vm, skip it");
                    continue;
                }

                long userId = CallContext.current().getCallingUserId();
                if (CallContext.current().getCallingAccount().getId() != owner.getId()) {
                    List<UserVO> userVOs = _userDao.listByAccount(owner.getAccountId());
                    if (!userVOs.isEmpty()) {
                        userId =  userVOs.get(0).getId();
                    }
                }

                internalLbVm =
                        new DomainRouterVO(id, routerOffering.getId(), internalLbProviderId, VirtualMachineName.getSystemVmName(id, _instance, InternalLbVmNamePrefix),
                                template.getId(), template.getHypervisorType(), template.getGuestOSId(), owner.getDomainId(), owner.getId(), userId, false, RedundantState.UNKNOWN, false, false, VirtualMachine.Type.InternalLoadBalancerVm, vpcId);
                internalLbVm.setRole(Role.INTERNAL_LB_VM);
                internalLbVm = _internalLbVmDao.persist(internalLbVm);
                _itMgr.allocate(internalLbVm.getInstanceName(), template, routerOffering, networks, plan, null);
                internalLbVm = _internalLbVmDao.findById(internalLbVm.getId());
            } catch (final InsufficientCapacityException ex) {
                if (allocateRetry < 2 && iter.hasNext()) {
                    s_logger.debug("Failed to allocate the Internal lb vm with hypervisor type " + hType + ", retrying one more time");
                    continue;
                } else {
                    throw ex;
                }
            } finally {
                allocateRetry++;
            }

            if (startVm) {
                try {
                    internalLbVm = startInternalLbVm(internalLbVm, _accountMgr.getSystemAccount(), User.UID_SYSTEM, params);
                    break;
                } catch (final InsufficientCapacityException ex) {
                    if (startRetry < 2 && iter.hasNext()) {
                        s_logger.debug("Failed to start the Internal lb vm  " + internalLbVm + " with hypervisor type " + hType + ", " +
                                "destroying it and recreating one more time");
                        // destroy the internal lb vm
                        destroyInternalLbVm(internalLbVm.getId(), _accountMgr.getSystemAccount(), User.UID_SYSTEM);
                        continue;
                    } else {
                        throw ex;
                    }
                } finally {
                    startRetry++;
                }
            } else {
                //return stopped internal lb vm
                return internalLbVm;
            }
        }
        return internalLbVm;
    }

};