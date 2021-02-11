//,temp,ExternalDeviceUsageManagerImpl.java,559,631,temp,ExternalDeviceUsageManagerImpl.java,208,294
//,3
public class xxx {
    @Override
    public void updateExternalLoadBalancerNetworkUsageStats(long loadBalancerRuleId) {

        LoadBalancerVO lb = _loadBalancerDao.findById(loadBalancerRuleId);
        if (lb == null) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Cannot update usage stats, LB rule is not found");
            }
            return;
        }
        long networkId = lb.getNetworkId();
        Network network = _networkDao.findById(networkId);
        if (network == null) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Cannot update usage stats, Network is not found");
            }
            return;
        }

        ExternalLoadBalancerDeviceVO lbDeviceVO = getExternalLoadBalancerForNetwork(network);
        if (lbDeviceVO == null) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Cannot update usage stats,  No external LB device found");
            }
            return;
        }

        // Get network stats from the external load balancer
        ExternalNetworkResourceUsageAnswer lbAnswer = null;
        HostVO externalLoadBalancer = _hostDao.findById(lbDeviceVO.getHostId());
        if (externalLoadBalancer != null) {
            ExternalNetworkResourceUsageCommand cmd = new ExternalNetworkResourceUsageCommand();
            lbAnswer = (ExternalNetworkResourceUsageAnswer)_agentMgr.easySend(externalLoadBalancer.getId(), cmd);
            if (lbAnswer == null || !lbAnswer.getResult()) {
                String details = (lbAnswer != null) ? lbAnswer.getDetails() : "details unavailable";
                String msg = "Unable to get external load balancer stats for network" + networkId + " due to: " + details + ".";
                s_logger.error(msg);
                return;
            }
        }

        long accountId = lb.getAccountId();
        AccountVO account = _accountDao.findById(accountId);
        if (account == null) {
            s_logger.debug("Skipping stats update for external LB for account with ID " + accountId);
            return;
        }

        String publicIp = _networkModel.getIp(lb.getSourceIpAddressId()).getAddress().addr();
        DataCenterVO zone = _dcDao.findById(network.getDataCenterId());
        String statsEntryIdentifier =
            "account " + account.getAccountName() + ", zone " + zone.getName() + ", network ID " + networkId + ", host ID " + externalLoadBalancer.getName();

        long newCurrentBytesSent = 0;
        long newCurrentBytesReceived = 0;

        if (publicIp != null) {
            long[] bytesSentAndReceived = null;
            statsEntryIdentifier += ", public IP: " + publicIp;
            boolean inline = _networkModel.isNetworkInlineMode(network);
            if (externalLoadBalancer.getType().equals(Host.Type.ExternalLoadBalancer) && inline) {
                // Look up stats for the guest IP address that's mapped to the public IP address
                InlineLoadBalancerNicMapVO mapping = _inlineLoadBalancerNicMapDao.findByPublicIpAddress(publicIp);

                if (mapping != null) {
                    NicVO nic = _nicDao.findById(mapping.getNicId());
                    String loadBalancingIpAddress = nic.getIPv4Address();
                    bytesSentAndReceived = lbAnswer.ipBytes.get(loadBalancingIpAddress);

                    if (bytesSentAndReceived != null) {
                        bytesSentAndReceived[0] = 0;
                    }
                }
            } else {
                bytesSentAndReceived = lbAnswer.ipBytes.get(publicIp);
            }

            if (bytesSentAndReceived == null) {
                s_logger.debug("Didn't get an external network usage answer for public IP " + publicIp);
            } else {
                newCurrentBytesSent += bytesSentAndReceived[0];
                newCurrentBytesReceived += bytesSentAndReceived[1];
            }

            commitStats(networkId, externalLoadBalancer, accountId, publicIp, zone, statsEntryIdentifier, newCurrentBytesSent, newCurrentBytesReceived);
        }
    }

};