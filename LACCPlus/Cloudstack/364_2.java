//,temp,ExternalLoadBalancerDeviceManagerImpl.java,328,372,temp,ExternalFirewallDeviceManagerImpl.java,279,308
//,3
public class xxx {
    @Override
    public boolean deleteExternalFirewall(Long hostId) {
        HostVO externalFirewall = _hostDao.findById(hostId);
        if (externalFirewall == null) {
            throw new InvalidParameterValueException("Could not find an external firewall with ID: " + hostId);
        }

        DetailVO fwHostDetails = _hostDetailDao.findDetail(hostId, ApiConstants.FIREWALL_DEVICE_ID);
        long fwDeviceId = Long.parseLong(fwHostDetails.getValue());

        // check if any networks are using this balancer device
        List<NetworkExternalFirewallVO> networks = _networkExternalFirewallDao.listByFirewallDeviceId(fwDeviceId);
        if ((networks != null) && !networks.isEmpty()) {
            throw new CloudRuntimeException("Delete can not be done as there are networks using the firewall device ");
        }

        try {
            // put the host in maintenance state in order for it to be deleted
            externalFirewall.setResourceState(ResourceState.Maintenance);
            _hostDao.update(hostId, externalFirewall);
            _resourceMgr.deleteHost(hostId, false, false);

            // delete the external load balancer entry
            _externalFirewallDeviceDao.remove(fwDeviceId);
            return true;
        } catch (Exception e) {
            s_logger.debug("Failed to delete external firewall device due to " + e.getMessage());
            return false;
        }
    }

};