//,temp,GuestNetworkGuru.java,231,251,temp,PublicNetworkGuru.java,199,221
//,3
public class xxx {
    @Override
    @DB
    public void deallocate(Network network, NicProfile nic, VirtualMachineProfile vm) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("public network deallocate network: networkId: " + nic.getNetworkId() + ", ip: " + nic.getIPv4Address());
        }

        final IPAddressVO ip = _ipAddressDao.findByIpAndSourceNetworkId(nic.getNetworkId(), nic.getIPv4Address());
        if (ip != null && nic.getReservationStrategy() != ReservationStrategy.Managed) {
            Transaction.execute(new TransactionCallbackNoReturn() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    _ipAddrMgr.markIpAsUnavailable(ip.getId());
                    _ipAddressDao.unassignIpAddress(ip.getId());
                }
            });
        }
        nic.deallocate();

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Deallocated nic: " + nic);
        }
    }

};