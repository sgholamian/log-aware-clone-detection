//,temp,VirtualNetworkApplianceManagerImpl.java,2401,2497,temp,UserVmManagerImpl.java,3958,4064
//,3
public class xxx {
    @Override
    public void prepareStop(final VirtualMachineProfile profile) {
        // Collect network usage before stopping Vm

        final DomainRouterVO router = _routerDao.findById(profile.getVirtualMachine().getId());
        if (router == null) {
            return;
        }

        final String privateIP = router.getPrivateIpAddress();

        if (privateIP != null) {
            final boolean forVpc = router.getVpcId() != null;
            final List<? extends Nic> routerNics = _nicDao.listByVmId(router.getId());
            for (final Nic routerNic : routerNics) {
                final Network network = _networkModel.getNetwork(routerNic.getNetworkId());
                // Send network usage command for public nic in VPC VR
                // Send network usage command for isolated guest nic of non VPC
                // VR
                if (forVpc && network.getTrafficType() == TrafficType.Public || !forVpc && network.getTrafficType() == TrafficType.Guest
                        && network.getGuestType() == Network.GuestType.Isolated) {
                    final NetworkUsageCommand usageCmd = new NetworkUsageCommand(privateIP, router.getHostName(), forVpc, routerNic.getIPv4Address());
                    final String routerType = router.getType().toString();
                    final UserStatisticsVO previousStats = _userStatsDao.findBy(router.getAccountId(), router.getDataCenterId(), network.getId(),
                            forVpc ? routerNic.getIPv4Address() : null, router.getId(), routerType);
                    NetworkUsageAnswer answer = null;
                    try {
                        answer = (NetworkUsageAnswer) _agentMgr.easySend(router.getHostId(), usageCmd);
                    } catch (final Exception e) {
                        s_logger.warn("Error while collecting network stats from router: " + router.getInstanceName() + " from host: " + router.getHostId(), e);
                        continue;
                    }

                    if (answer != null) {
                        if (!answer.getResult()) {
                            s_logger.warn("Error while collecting network stats from router: " + router.getInstanceName() + " from host: " + router.getHostId() + "; details: "
                                    + answer.getDetails());
                            continue;
                        }
                        try {
                            if (answer.getBytesReceived() == 0 && answer.getBytesSent() == 0) {
                                s_logger.debug("Recieved and Sent bytes are both 0. Not updating user_statistics");
                                continue;
                            }

                            final NetworkUsageAnswer answerFinal = answer;
                            Transaction.execute(new TransactionCallbackNoReturn() {
                                @Override
                                public void doInTransactionWithoutResult(final TransactionStatus status) {
                                    final UserStatisticsVO stats = _userStatsDao.lock(router.getAccountId(), router.getDataCenterId(), network.getId(),
                                            forVpc ? routerNic.getIPv4Address() : null, router.getId(), routerType);
                                    if (stats == null) {
                                        s_logger.warn("unable to find stats for account: " + router.getAccountId());
                                        return;
                                    }

                                    if (previousStats != null
                                            && (previousStats.getCurrentBytesReceived() != stats.getCurrentBytesReceived() || previousStats.getCurrentBytesSent() != stats
                                            .getCurrentBytesSent())) {
                                        s_logger.debug("Router stats changed from the time NetworkUsageCommand was sent. " + "Ignoring current answer. Router: "
                                                + answerFinal.getRouterName() + " Rcvd: " + answerFinal.getBytesReceived() + "Sent: " + answerFinal.getBytesSent());
                                        return;
                                    }

                                    if (stats.getCurrentBytesReceived() > answerFinal.getBytesReceived()) {
                                        if (s_logger.isDebugEnabled()) {
                                            s_logger.debug("Received # of bytes that's less than the last one.  " + "Assuming something went wrong and persisting it. Router: "
                                                    + answerFinal.getRouterName() + " Reported: " + answerFinal.getBytesReceived() + " Stored: " + stats.getCurrentBytesReceived());
                                        }
                                        stats.setNetBytesReceived(stats.getNetBytesReceived() + stats.getCurrentBytesReceived());
                                    }
                                    stats.setCurrentBytesReceived(answerFinal.getBytesReceived());
                                    if (stats.getCurrentBytesSent() > answerFinal.getBytesSent()) {
                                        if (s_logger.isDebugEnabled()) {
                                            s_logger.debug("Received # of bytes that's less than the last one.  " + "Assuming something went wrong and persisting it. Router: "
                                                    + answerFinal.getRouterName() + " Reported: " + answerFinal.getBytesSent() + " Stored: " + stats.getCurrentBytesSent());
                                        }
                                        stats.setNetBytesSent(stats.getNetBytesSent() + stats.getCurrentBytesSent());
                                    }
                                    stats.setCurrentBytesSent(answerFinal.getBytesSent());
                                    if (!_dailyOrHourly) {
                                        // update agg bytes
                                        stats.setAggBytesSent(stats.getNetBytesSent() + stats.getCurrentBytesSent());
                                        stats.setAggBytesReceived(stats.getNetBytesReceived() + stats.getCurrentBytesReceived());
                                    }
                                    _userStatsDao.update(stats.getId(), stats);
                                }
                            });
                        } catch (final Exception e) {
                            s_logger.warn("Unable to update user statistics for account: " + router.getAccountId() + " Rx: " + answer.getBytesReceived() + "; Tx: "
                                    + answer.getBytesSent());
                        }
                    }
                }
            }
        }
    }

};