//,temp,OvsTunnelManagerImpl.java,561,672,temp,OvsTunnelManagerImpl.java,309,400
//,3
public class xxx {
    @DB
    protected void checkAndCreateVpcTunnelNetworks(Host host, long vpcId) {

        long hostId = host.getId();
        String bridgeName=generateBridgeNameForVpc(vpcId);

        List<Long> vmIds = _ovsNetworkToplogyGuru.getActiveVmsInVpcOnHost(vpcId, hostId);

        if (vmIds == null || vmIds.isEmpty()) {

            // since this is the first VM from the VPC being launched on the host, first setup the bridge
            try {
                Commands cmds = new Commands(new OvsSetupBridgeCommand(bridgeName, hostId, null));
                s_logger.debug("Ask host " + hostId + " to create bridge for vpc " + vpcId + " and configure the "
                        + " bridge for distributed routing.");
                Answer[] answers = _agentMgr.send(hostId, cmds);
                handleSetupBridgeAnswer(answers);
            } catch (OperationTimedoutException | AgentUnavailableException e) {
                s_logger.warn("Ovs Tunnel network created bridge failed", e);
            }

            // now that bridge is setup, populate network acl's before the VM gets created
            OvsVpcRoutingPolicyConfigCommand cmd = prepareVpcRoutingPolicyUpdate(vpcId);
            cmd.setSequenceNumber(getNextRoutingPolicyUpdateSequenceNumber(vpcId));

            if (!sendVpcRoutingPolicyChangeUpdate(cmd, hostId, bridgeName)) {
                s_logger.debug("Failed to send VPC routing policy change update to host : " + hostId +
                        ". But moving on with sending the updates to the rest of the hosts.");
            }
        }

        List<? extends Network> vpcNetworks =  _vpcMgr.getVpcNetworks(vpcId);
        List<Long> vpcSpannedHostIds = _ovsNetworkToplogyGuru.getVpcSpannedHosts(vpcId);
        for (Network vpcNetwork: vpcNetworks) {
            if (vpcNetwork.getState() != Network.State.Implemented &&
                    vpcNetwork.getState() != Network.State.Implementing && vpcNetwork.getState() != Network.State.Setup)
                continue;

            int key = getGreKey(vpcNetwork);
            List<Long> toHostIds = new ArrayList<Long>();
            List<Long> fromHostIds = new ArrayList<Long>();
            OvsTunnelNetworkVO tunnelRecord = null;

            for (Long rh : vpcSpannedHostIds) {
                if (rh == hostId) {
                    continue;
                }
                tunnelRecord = _tunnelNetworkDao.findByFromToNetwork(hostId, rh.longValue(), vpcNetwork.getId());
                // Try and create the tunnel if does not exit or previous attempt failed
                if (tunnelRecord == null || tunnelRecord.getState().equals(OvsTunnel.State.Failed.name())) {
                    s_logger.debug("Attempting to create tunnel from:" + hostId + " to:" + rh.longValue());
                    if (tunnelRecord == null) {
                        createTunnelRecord(hostId, rh.longValue(), vpcNetwork.getId(), key);
                    }
                    if (!toHostIds.contains(rh)) {
                        toHostIds.add(rh);
                    }
                }
                tunnelRecord = _tunnelNetworkDao.findByFromToNetwork(rh.longValue(), hostId, vpcNetwork.getId());
                // Try and create the tunnel if does not exit or previous attempt failed
                if (tunnelRecord == null || tunnelRecord.getState().equals(OvsTunnel.State.Failed.name())) {
                    s_logger.debug("Attempting to create tunnel from:" + rh.longValue() + " to:" + hostId);
                    if (tunnelRecord == null) {
                        createTunnelRecord(rh.longValue(), hostId, vpcNetwork.getId(), key);
                    }
                    if (!fromHostIds.contains(rh)) {
                        fromHostIds.add(rh);
                    }
                }
            }

            try {
                String myIp = getGreEndpointIP(host, vpcNetwork);
                if (myIp == null)
                    throw new GreTunnelException("Unable to retrieve the source " + "endpoint for the GRE tunnel."
                            + "Failure is on host:" + host.getId());
                boolean noHost = true;

                for (Long i : toHostIds) {
                    HostVO rHost = _hostDao.findById(i);
                    String otherIp = getGreEndpointIP(rHost, vpcNetwork);
                    if (otherIp == null)
                        throw new GreTunnelException(
                                "Unable to retrieve the remote endpoint for the GRE tunnel."
                                        + "Failure is on host:" + rHost.getId());
                    Commands cmds = new Commands( new OvsCreateTunnelCommand(otherIp, key, Long.valueOf(hostId),
                                     i, vpcNetwork.getId(), myIp, bridgeName, vpcNetwork.getUuid()));
                    s_logger.debug("Attempting to create tunnel from:" + hostId + " to:" + i + " for the network "
                            + vpcNetwork.getId());
                    s_logger.debug("Ask host " + hostId
                            + " to create gre tunnel to " + i);
                    Answer[] answers = _agentMgr.send(hostId, cmds);
                    handleCreateTunnelAnswer(answers);
                }

                for (Long i : fromHostIds) {
                    HostVO rHost = _hostDao.findById(i);
                    String otherIp = getGreEndpointIP(rHost, vpcNetwork);
                    Commands cmds = new Commands(new OvsCreateTunnelCommand(myIp,
                            key, i, Long.valueOf(hostId), vpcNetwork.getId(), otherIp, bridgeName,
                            vpcNetwork.getUuid()));
                    s_logger.debug("Ask host " + i + " to create gre tunnel to "
                            + hostId);
                    Answer[] answers = _agentMgr.send(i, cmds);
                    handleCreateTunnelAnswer(answers);
                }
            } catch (GreTunnelException | OperationTimedoutException | AgentUnavailableException e) {
                // I really thing we should do a better handling of these exceptions
                s_logger.warn("Ovs Tunnel network created tunnel failed", e);
            }
        }
    }

};