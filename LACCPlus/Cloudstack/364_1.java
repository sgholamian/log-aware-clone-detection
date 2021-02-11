//,temp,ExternalLoadBalancerDeviceManagerImpl.java,328,372,temp,ExternalFirewallDeviceManagerImpl.java,279,308
//,3
public class xxx {
    @Override
    public boolean deleteExternalLoadBalancer(long hostId) {
        HostVO externalLoadBalancer = _hostDao.findById(hostId);
        if (externalLoadBalancer == null) {
            throw new InvalidParameterValueException("Could not find an external load balancer with ID: " + hostId);
        }

        DetailVO lbHostDetails = _hostDetailDao.findDetail(hostId, ApiConstants.LOAD_BALANCER_DEVICE_ID);
        long lbDeviceId = Long.parseLong(lbHostDetails.getValue());

        ExternalLoadBalancerDeviceVO lbDeviceVo = _externalLoadBalancerDeviceDao.findById(lbDeviceId);
        if (lbDeviceVo.getAllocationState() == LBDeviceAllocationState.Provider) {
            // check if cloudstack has provisioned any load balancer appliance on the device before deleting
            List<ExternalLoadBalancerDeviceVO> lbDevices = _externalLoadBalancerDeviceDao.listAll();
            if (lbDevices != null) {
                for (ExternalLoadBalancerDeviceVO lbDevice : lbDevices) {
                    if (lbDevice.getParentHostId() == hostId) {
                        throw new CloudRuntimeException(
                            "This load balancer device can not be deleted as there are one or more load balancers applainces provisioned by cloudstack on the device.");
                    }
                }
            }
        } else {
            // check if any networks are using this load balancer device
            List<NetworkExternalLoadBalancerVO> networks = _networkLBDao.listByLoadBalancerDeviceId(lbDeviceId);
            if ((networks != null) && !networks.isEmpty()) {
                throw new CloudRuntimeException("Delete can not be done as there are networks using this load balancer device ");
            }
        }

        try {
            // put the host in maintenance state in order for it to be deleted
            externalLoadBalancer.setResourceState(ResourceState.Maintenance);
            _hostDao.update(hostId, externalLoadBalancer);
            _resourceMgr.deleteHost(hostId, false, false);

            // delete the external load balancer entry
            _externalLoadBalancerDeviceDao.remove(lbDeviceId);

            return true;
        } catch (Exception e) {
            s_logger.debug(e);
            return false;
        }
    }

};