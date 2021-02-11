//,temp,ContrailElementImpl.java,120,143,temp,ContrailGuru.java,141,181
//,3
public class xxx {
    @Override
    public Network implement(Network network, NetworkOffering offering, DeployDestination destination, ReservationContext context)
            throws InsufficientVirtualNetworkCapacityException {
        s_logger.debug("Implement network: " + network.getName() + ", traffic type: " + network.getTrafficType());

        VirtualNetworkModel vnModel = _manager.getDatabase().lookupVirtualNetwork(network.getUuid(), _manager.getCanonicalName(network), network.getTrafficType());
        if (vnModel == null) {
            vnModel = new VirtualNetworkModel(network, network.getUuid(), _manager.getCanonicalName(network), network.getTrafficType());
            vnModel.setProperties(_manager.getModelController(), network);
        }

        try {
            if (!vnModel.verify(_manager.getModelController())) {
                vnModel.update(_manager.getModelController());
            }
        } catch (Exception ex) {
            s_logger.warn("virtual-network update: ", ex);
            return network;
        }
        _manager.getDatabase().getVirtualNetworks().add(vnModel);

        if (network.getVpcId() != null) {
            List<IPAddressVO> ips = _ipAddressDao.listByAssociatedVpc(network.getVpcId(), true);
            if (ips.isEmpty()) {
                s_logger.debug("Creating a source nat ip for network " + network);
                Account owner = _accountMgr.getAccount(network.getAccountId());
                try {
                    PublicIp publicIp = _ipAddrMgr.assignSourceNatIpAddressToGuestNetwork(owner, network);
                    IPAddressVO ip = publicIp.ip();
                    ip.setVpcId(network.getVpcId());
                    _ipAddressDao.acquireInLockTable(ip.getId());
                    _ipAddressDao.update(ip.getId(), ip);
                    _ipAddressDao.releaseFromLockTable(ip.getId());
                } catch (Exception e) {
                    s_logger.error("Unable to allocate source nat ip: " + e);
                }
            }
        }

        return network;
    }

};