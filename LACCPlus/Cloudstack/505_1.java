//,temp,ExternalDeviceUsageManagerImpl.java,559,631,temp,ExternalDeviceUsageManagerImpl.java,208,294
//,3
public class xxx {
        private boolean updateStatsEntry(long accountId, long zoneId, long networkId, String publicIp, long hostId, ExternalNetworkResourceUsageAnswer answer,
            boolean inline) {
            AccountVO account = _accountDao.findById(accountId);
            DataCenterVO zone = _dcDao.findById(zoneId);
            NetworkVO network = _networkDao.findById(networkId);
            HostVO host = _hostDao.findById(hostId);
            String statsEntryIdentifier =
                "account " + account.getAccountName() + ", zone " + zone.getName() + ", network ID " + networkId + ", host ID " + host.getName();

            long newCurrentBytesSent = 0;
            long newCurrentBytesReceived = 0;

            if (publicIp != null) {
                long[] bytesSentAndReceived = null;
                statsEntryIdentifier += ", public IP: " + publicIp;

                if (host.getType().equals(Host.Type.ExternalLoadBalancer) && inline) {
                    // Look up stats for the guest IP address that's mapped to the public IP address
                    InlineLoadBalancerNicMapVO mapping = _inlineLoadBalancerNicMapDao.findByPublicIpAddress(publicIp);

                    if (mapping != null) {
                        NicVO nic = _nicDao.findById(mapping.getNicId());
                        String loadBalancingIpAddress = nic.getIPv4Address();
                        bytesSentAndReceived = answer.ipBytes.get(loadBalancingIpAddress);

                        if (bytesSentAndReceived != null) {
                            bytesSentAndReceived[0] = 0;
                        }
                    }
                } else {
                    bytesSentAndReceived = answer.ipBytes.get(publicIp);
                }

                if (bytesSentAndReceived == null) {
                    s_logger.debug("Didn't get an external network usage answer for public IP " + publicIp);
                } else {
                    newCurrentBytesSent += bytesSentAndReceived[0];
                    newCurrentBytesReceived += bytesSentAndReceived[1];
                }
            } else {
                URI broadcastURI = network.getBroadcastUri();
                if (broadcastURI == null) {
                    s_logger.debug("Not updating stats for guest network with ID " + network.getId() + " because the network is not implemented.");
                    return true;
                } else {
                    long vlanTag = Integer.parseInt(BroadcastDomainType.getValue(broadcastURI));
                    long[] bytesSentAndReceived = answer.guestVlanBytes.get(String.valueOf(vlanTag));

                    if (bytesSentAndReceived == null) {
                        s_logger.warn("Didn't get an external network usage answer for guest VLAN " + vlanTag);
                    } else {
                        newCurrentBytesSent += bytesSentAndReceived[0];
                        newCurrentBytesReceived += bytesSentAndReceived[1];
                    }
                }
            }

            UserStatisticsVO userStats;
            try {
                userStats = _userStatsDao.lock(accountId, zoneId, networkId, publicIp, hostId, host.getType().toString());
            } catch (Exception e) {
                s_logger.warn("Unable to find user stats entry for " + statsEntryIdentifier);
                return false;
            }

            if (updateBytes(userStats, newCurrentBytesSent, newCurrentBytesReceived)) {
                s_logger.debug("Successfully updated stats for " + statsEntryIdentifier);
                return true;
            } else {
                s_logger.debug("Failed to update stats for " + statsEntryIdentifier);
                return false;
            }
        }

};