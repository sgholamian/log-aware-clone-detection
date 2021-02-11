//,temp,VpcVirtualRouterElement.java,244,282,temp,VpcVirtualRouterElement.java,182,222
//,3
public class xxx {
    @Override
    public boolean prepare(final Network network, final NicProfile nic, final VirtualMachineProfile vm, final DeployDestination dest, final ReservationContext context)
            throws ConcurrentOperationException, InsufficientCapacityException, ResourceUnavailableException {

        final Long vpcId = network.getVpcId();
        if (vpcId == null) {
            s_logger.trace("Network " + network + " is not associated with any VPC");
            return false;
        }

        final Vpc vpc = _vpcMgr.getActiveVpc(vpcId);
        if (vpc == null) {
            s_logger.warn("Unable to find Enabled VPC by id " + vpcId);
            return false;
        }

        if (vm.getType() == VirtualMachine.Type.User) {
            final Map<VirtualMachineProfile.Param, Object> params = new HashMap<VirtualMachineProfile.Param, Object>(1);
            params.put(VirtualMachineProfile.Param.ReProgramGuestNetworks, true);

            final RouterDeploymentDefinition routerDeploymentDefinition = routerDeploymentDefinitionBuilder.create()
                    .setGuestNetwork(network)
                    .setVpc(vpc)
                    .setDeployDestination(dest)
                    .setAccountOwner(_accountMgr.getAccount(vpc.getAccountId()))
                    .setParams(params)
                    .build();

            final List<DomainRouterVO> routers = routerDeploymentDefinition.deployVirtualRouter();

            if (routers == null || routers.size() == 0) {
                throw new ResourceUnavailableException("Can't find at least one running router!", DataCenter.class, network.getDataCenterId());
            }

            configureGuestNetwork(network, routers);
        }

        return true;
    }

};