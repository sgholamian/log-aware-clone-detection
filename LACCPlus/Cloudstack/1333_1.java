//,temp,ResourceManagerImpl.java,2038,2143,temp,ResourceManagerImpl.java,1969,2036
//,3
public class xxx {
    private Host createHostAndAgentDeferred(final ServerResource resource, final Map<String, String> details, final boolean old, final List<String> hostTags, final boolean forRebalance) {
        HostVO host = null;
        StartupCommand[] cmds = null;
        boolean hostExists = false;
        boolean deferAgentCreation = true;
        boolean created = false;

        try {
            cmds = resource.initialize();
            if (cmds == null) {
                s_logger.info("Unable to fully initialize the agent because no StartupCommands are returned");
                return null;
            }

            /* Generate a random version in a dev setup situation */
            if (this.getClass().getPackage().getImplementationVersion() == null) {
                for (final StartupCommand cmd : cmds) {
                    if (cmd.getVersion() == null) {
                        cmd.setVersion(Long.toString(System.currentTimeMillis()));
                    }
                }
            }

            if (s_logger.isDebugEnabled()) {
                new Request(-1l, -1l, cmds, true, false).logD("Startup request from directly connected host: ", true);
            }

            if (old) {
                final StartupCommand firstCmd = cmds[0];
                host = findHostByGuid(firstCmd.getGuid());
                if (host == null) {
                    host = findHostByGuid(firstCmd.getGuidWithoutResource());
                }
                if (host != null && host.getRemoved() == null) { // host already
                    // added, no
                    // need to add
                    // again
                    s_logger.debug("Found the host " + host.getId() + " by guid: " + firstCmd.getGuid() + ", old host reconnected as new");
                    hostExists = true; // ensures that host status is left
                    // unchanged in case of adding same one
                    // again
                    return null;
                }
            }

            host = null;
            boolean newHost = false;

            final GlobalLock addHostLock = GlobalLock.getInternLock("AddHostLock");

            try {
                if (addHostLock.lock(ACQUIRE_GLOBAL_LOCK_TIMEOUT_FOR_COOPERATION)) {
                    // to safely determine first host in cluster in multi-MS scenario
                    try {
                        // find out if the host we want to connect to is new (so we can send an event)
                        newHost = getNewHost(cmds) == null;

                        host = createHostVO(cmds, resource, details, hostTags, ResourceStateAdapter.Event.CREATE_HOST_VO_FOR_DIRECT_CONNECT);

                        if (host != null) {
                            // if first host in cluster no need to defer agent creation
                            deferAgentCreation = !isFirstHostInCluster(host);
                        }
                    } finally {
                        addHostLock.unlock();
                    }
                }
            } finally {
                addHostLock.releaseRef();
            }

            if (host != null) {
                if (!deferAgentCreation) { // if first host in cluster then
                    created = _agentMgr.handleDirectConnectAgent(host, cmds, resource, forRebalance, newHost);
                    host = _hostDao.findById(host.getId()); // reload
                } else {
                    host = _hostDao.findById(host.getId()); // reload
                    // force host status to 'Alert' so that it is loaded for
                    // connection during next scan task
                    _agentMgr.agentStatusTransitTo(host, Status.Event.AgentDisconnected, _nodeId);

                    host = _hostDao.findById(host.getId()); // reload
                    host.setLastPinged(0); // so that scan task can pick it up
                    _hostDao.update(host.getId(), host);

                }
            }
        } catch (final Exception e) {
            s_logger.warn("Unable to connect due to ", e);
        } finally {
            if (hostExists) {
                if (cmds != null) {
                    resource.disconnected();
                }
            } else {
                if (!deferAgentCreation && !created) {
                    if (cmds != null) {
                        resource.disconnected();
                    }
                    markHostAsDisconnected(host, cmds);
                }
            }
        }

        return host;
    }

};