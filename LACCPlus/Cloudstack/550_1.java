//,temp,NetworkHelperImpl.java,450,540,temp,InternalLoadBalancerVMManagerImpl.java,724,821
//,3
public class xxx {
    @Override
    public DomainRouterVO deployRouter(final RouterDeploymentDefinition routerDeploymentDefinition, final boolean startRouter)
            throws InsufficientAddressCapacityException, InsufficientServerCapacityException, InsufficientCapacityException, StorageUnavailableException, ResourceUnavailableException {

        final ServiceOfferingVO routerOffering = _serviceOfferingDao.findById(routerDeploymentDefinition.getServiceOfferingId());
        final Account owner = routerDeploymentDefinition.getOwner();

        // Router is the network element, we don't know the hypervisor type yet.
        // Try to allocate the domR twice using diff hypervisors, and when
        // failed both times, throw the exception up
        final List<HypervisorType> hypervisors = getHypervisors(routerDeploymentDefinition);

        int allocateRetry = 0;
        int startRetry = 0;
        DomainRouterVO router = null;
        for (final Iterator<HypervisorType> iter = hypervisors.iterator(); iter.hasNext();) {
            final HypervisorType hType = iter.next();
            try {
                final long id = _routerDao.getNextInSequence(Long.class, "id");
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(String.format("Allocating the VR with id=%s in datacenter %s with the hypervisor type %s", id, routerDeploymentDefinition.getDest()
                            .getDataCenter(), hType));
                }

                final String templateName = retrieveTemplateName(hType, routerDeploymentDefinition.getDest().getDataCenter().getId());
                final VMTemplateVO template = _templateDao.findRoutingTemplate(hType, templateName);

                if (template == null) {
                    s_logger.debug(hType + " won't support system vm, skip it");
                    continue;
                }

                final boolean offerHA = routerOffering.isOfferHA();

                // routerDeploymentDefinition.getVpc().getId() ==> do not use
                // VPC because it is not a VPC offering.
                final Long vpcId = routerDeploymentDefinition.getVpc() != null ? routerDeploymentDefinition.getVpc().getId() : null;

                long userId = CallContext.current().getCallingUserId();
                if (CallContext.current().getCallingAccount().getId() != owner.getId()) {
                    final List<UserVO> userVOs = _userDao.listByAccount(owner.getAccountId());
                    if (!userVOs.isEmpty()) {
                        userId =  userVOs.get(0).getId();
                    }
                }

                router = new DomainRouterVO(id, routerOffering.getId(), routerDeploymentDefinition.getVirtualProvider().getId(), VirtualMachineName.getRouterName(id,
                        s_vmInstanceName), template.getId(), template.getHypervisorType(), template.getGuestOSId(), owner.getDomainId(), owner.getId(),
                        userId, routerDeploymentDefinition.isRedundant(), RedundantState.UNKNOWN, offerHA, false, vpcId);

                router.setDynamicallyScalable(template.isDynamicallyScalable());
                router.setRole(Role.VIRTUAL_ROUTER);
                router = _routerDao.persist(router);

                reallocateRouterNetworks(routerDeploymentDefinition, router, template, null);
                router = _routerDao.findById(router.getId());
            } catch (final InsufficientCapacityException ex) {
                if (allocateRetry < 2 && iter.hasNext()) {
                    s_logger.debug("Failed to allocate the VR with hypervisor type " + hType + ", retrying one more time");
                    continue;
                } else {
                    throw ex;
                }
            } finally {
                allocateRetry++;
            }

            if (startRouter) {
                try {
                    router = startVirtualRouter(router, _accountMgr.getSystemUser(), _accountMgr.getSystemAccount(), routerDeploymentDefinition.getParams());
                    break;
                } catch (final InsufficientCapacityException ex) {
                    if (startRetry < 2 && iter.hasNext()) {
                        s_logger.debug("Failed to start the VR  " + router + " with hypervisor type " + hType + ", " + "destroying it and recreating one more time");
                        // destroy the router
                        destroyRouter(router.getId(), _accountMgr.getAccount(Account.ACCOUNT_ID_SYSTEM), User.UID_SYSTEM);
                        continue;
                    } else {
                        throw ex;
                    }
                } finally {
                    startRetry++;
                }
            } else {
                // return stopped router
                return router;
            }
        }

        return router;
    }

};