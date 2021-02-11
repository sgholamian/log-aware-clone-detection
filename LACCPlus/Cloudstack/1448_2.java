//,temp,OvsTunnelManagerImpl.java,561,672,temp,OvsTunnelManagerImpl.java,309,400
//,3
public class xxx {
    @DB
    protected void checkAndCreateTunnel(Network nw, Host host) {

        s_logger.debug("Creating tunnels with OVS tunnel manager");

        long hostId = host.getId();
        int key = getGreKey(nw);
        String bridgeName = generateBridgeName(nw, key);
        List<Long> toHostIds = new ArrayList<Long>();
        List<Long> fromHostIds = new ArrayList<Long>();
        List<Long> networkSpannedHosts = _ovsNetworkToplogyGuru.getNetworkSpanedHosts(nw.getId());
        for (Long rh : networkSpannedHosts) {
            if (rh == hostId) {
                continue;
            }
            OvsTunnelNetworkVO ta = _tunnelNetworkDao.findByFromToNetwork(hostId, rh.longValue(), nw.getId());
            // Try and create the tunnel even if a previous attempt failed
            if (ta == null || ta.getState().equals(OvsTunnel.State.Failed.name())) {
                s_logger.debug("Attempting to create tunnel from:" + hostId + " to:" + rh.longValue());
                if (ta == null) {
                    createTunnelRecord(hostId, rh.longValue(), nw.getId(), key);
                }
                if (!toHostIds.contains(rh)) {
                    toHostIds.add(rh);
                }
            }

            ta = _tunnelNetworkDao.findByFromToNetwork(rh.longValue(),
                    hostId, nw.getId());
            // Try and create the tunnel even if a previous attempt failed
            if (ta == null || ta.getState().equals(OvsTunnel.State.Failed.name())) {
                s_logger.debug("Attempting to create tunnel from:" +
                        rh.longValue() + " to:" + hostId);
                if (ta == null) {
                    createTunnelRecord(rh.longValue(), hostId,
                            nw.getId(), key);
                }
                if (!fromHostIds.contains(rh)) {
                    fromHostIds.add(rh);
                }
            }
        }
        //TODO: Should we propagate the exception here?
        try {
            String myIp = getGreEndpointIP(host, nw);
            if (myIp == null)
                throw new GreTunnelException("Unable to retrieve the source " + "endpoint for the GRE tunnel." + "Failure is on host:" + host.getId());
            boolean noHost = true;
            for (Long i : toHostIds) {
                HostVO rHost = _hostDao.findById(i);
                String otherIp = getGreEndpointIP(rHost, nw);
                if (otherIp == null)
                    throw new GreTunnelException(
                            "Unable to retrieve the remote "
                                    + "endpoint for the GRE tunnel."
                                    + "Failure is on host:" + rHost.getId());
                Commands cmds = new Commands(
                        new OvsCreateTunnelCommand(otherIp, key,
                                Long.valueOf(hostId), i, nw.getId(), myIp, bridgeName, nw.getUuid()));
                s_logger.debug("Attempting to create tunnel from:" + hostId + " to:" + i + " for the network " + nw.getId());
                s_logger.debug("Ask host " + hostId
                        + " to create gre tunnel to " + i);
                Answer[] answers = _agentMgr.send(hostId, cmds);
                handleCreateTunnelAnswer(answers);
                noHost = false;
            }

            for (Long i : fromHostIds) {
                HostVO rHost = _hostDao.findById(i);
                String otherIp = getGreEndpointIP(rHost, nw);
                Commands cmds = new Commands(new OvsCreateTunnelCommand(myIp,
                        key, i, Long.valueOf(hostId), nw.getId(), otherIp, bridgeName, nw.getUuid()));
                s_logger.debug("Ask host " + i + " to create gre tunnel to "
                        + hostId);
                Answer[] answers = _agentMgr.send(i, cmds);
                handleCreateTunnelAnswer(answers);
                noHost = false;
            }

            // If no tunnels have been configured, perform the bridge setup
            // anyway. This will ensure VIF rules will be triggered
            if (noHost) {
                Commands cmds = new Commands(new OvsSetupBridgeCommand(bridgeName, hostId, nw.getId()));
                s_logger.debug("Ask host " + hostId + " to configure bridge for network:" + nw.getId());
                Answer[] answers = _agentMgr.send(hostId, cmds);
                handleSetupBridgeAnswer(answers);
            }
        } catch (GreTunnelException | OperationTimedoutException | AgentUnavailableException e) {
            // I really thing we should do a better handling of these exceptions
            s_logger.warn("Ovs Tunnel network created tunnel failed", e);
        }
    }

};