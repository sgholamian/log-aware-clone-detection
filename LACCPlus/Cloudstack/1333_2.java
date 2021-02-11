//,temp,ResourceManagerImpl.java,2038,2143,temp,ResourceManagerImpl.java,1969,2036
//,3
public class xxx {
    private Host createHostAndAgent(final ServerResource resource, final Map<String, String> details, final boolean old, final List<String> hostTags, final boolean forRebalance) {
        HostVO host = null;
        StartupCommand[] cmds = null;
        boolean hostExists = false;
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
                if (host != null && host.getRemoved() == null) { // host already added, no need to add again
                    s_logger.debug("Found the host " + host.getId() + " by guid: " + firstCmd.getGuid() + ", old host reconnected as new");
                    hostExists = true; // ensures that host status is left unchanged in case of adding same one again
                    return null;
                }
            }

            // find out if the host we want to connect to is new (so we can send an event)
            boolean newHost = getNewHost(cmds) == null;

            host = createHostVO(cmds, resource, details, hostTags, ResourceStateAdapter.Event.CREATE_HOST_VO_FOR_DIRECT_CONNECT);

            if (host != null) {
                created = _agentMgr.handleDirectConnectAgent(host, cmds, resource, forRebalance, newHost);
                /* reload myself from database */
                host = _hostDao.findById(host.getId());
            }
        } catch (final Exception e) {
            s_logger.warn("Unable to connect due to ", e);
        } finally {
            if (hostExists) {
                if (cmds != null) {
                    resource.disconnected();
                }
            } else {
                if (!created) {
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