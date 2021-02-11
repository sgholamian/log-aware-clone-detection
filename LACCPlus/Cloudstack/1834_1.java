//,temp,NiciraNvpElement.java,803,842,temp,NiciraNvpElement.java,453,491
//,3
public class xxx {
    @Override
    public boolean applyIps(Network network, List<? extends PublicIpAddress> ipAddress, Set<Service> services) throws ResourceUnavailableException {
        if (services.contains(Service.SourceNat)) {
            // Only if we need to provide SourceNat we need to configure the logical router
            // SourceNat is required for StaticNat and PortForwarding
            List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
            if (devices.isEmpty()) {
                s_logger.error("No NiciraNvp Controller on physical network " + network.getPhysicalNetworkId());
                return false;
            }
            NiciraNvpDeviceVO niciraNvpDevice = devices.get(0);
            HostVO niciraNvpHost = hostDao.findById(niciraNvpDevice.getHostId());
            hostDao.loadDetails(niciraNvpHost);

            NiciraNvpRouterMappingVO routermapping = niciraNvpRouterMappingDao.findByNetworkId(network.getId());
            if (routermapping == null) {
                s_logger.error("No logical router uuid found for network " + network.getDisplayText());
                return false;
            }

            List<String> cidrs = new ArrayList<String>();
            for (PublicIpAddress ip : ipAddress) {
                if (ip.getState() == IpAddress.State.Releasing) {
                    // If we are releasing we don't need to push this ip to
                    // the Logical Router
                    continue;
                }
                cidrs.add(ip.getAddress().addr() + "/" + NetUtils.getCidrSize(ip.getNetmask()));
            }
            ConfigurePublicIpsOnLogicalRouterCommand cmd =
                    new ConfigurePublicIpsOnLogicalRouterCommand(routermapping.getLogicalRouterUuid(), niciraNvpHost.getDetail("l3gatewayserviceuuid"), cidrs);
            ConfigurePublicIpsOnLogicalRouterAnswer answer = (ConfigurePublicIpsOnLogicalRouterAnswer)agentMgr.easySend(niciraNvpHost.getId(), cmd);
            //FIXME answer can be null if the host is down
            return answer.getResult();
        } else {
            s_logger.debug("No need to provision ip addresses as we are not providing L3 services.");
        }

        return true;
    }

};