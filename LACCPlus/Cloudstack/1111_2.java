//,temp,ExternalDeviceUsageManagerImpl.java,523,545,temp,ExternalDeviceUsageManagerImpl.java,299,333
//,3
public class xxx {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                UserStatisticsVO userStats;
                userStats = _userStatsDao.lock(accountId, zone.getId(), networkId, publicIp, externalLoadBalancer.getId(), externalLoadBalancer.getType().toString());

                if (userStats != null) {
                    long oldNetBytesSent = userStats.getNetBytesSent();
                    long oldNetBytesReceived = userStats.getNetBytesReceived();
                    long oldCurrentBytesSent = userStats.getCurrentBytesSent();
                    long oldCurrentBytesReceived = userStats.getCurrentBytesReceived();
                    String warning =
                        "Received an external network stats byte count that was less than the stored value. Zone ID: " + userStats.getDataCenterId() + ", account ID: " +
                            userStats.getAccountId() + ".";

                    userStats.setCurrentBytesSent(newCurrentBytesSent);
                    if (oldCurrentBytesSent > newCurrentBytesSent) {
                        s_logger.warn(warning + "Stored bytes sent: " + oldCurrentBytesSent + ", new bytes sent: " + newCurrentBytesSent + ".");
                        userStats.setNetBytesSent(oldNetBytesSent + oldCurrentBytesSent);
                    }

                    userStats.setCurrentBytesReceived(newCurrentBytesReceived);
                    if (oldCurrentBytesReceived > newCurrentBytesReceived) {
                        s_logger.warn(warning + "Stored bytes received: " + oldCurrentBytesReceived + ", new bytes received: " + newCurrentBytesReceived + ".");
                        userStats.setNetBytesReceived(oldNetBytesReceived + oldCurrentBytesReceived);
                    }

                    if (_userStatsDao.update(userStats.getId(), userStats)) {
                        s_logger.debug("Successfully updated stats for " + statsEntryIdentifier);
                    } else {
                        s_logger.debug("Failed to update stats for " + statsEntryIdentifier);
                    }
                } else {
                    s_logger.warn("Unable to find user stats entry for " + statsEntryIdentifier);
                }
            }

};