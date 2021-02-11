//,temp,GuestNetworkGuru.java,231,251,temp,PublicNetworkGuru.java,199,221
//,3
public class xxx {
    @Override
    @DB
    public void deallocate(final Network network, final NicProfile nic, final VirtualMachineProfile vm) {
        if (network.getSpecifyIpRanges()) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Deallocate network: networkId: " + nic.getNetworkId() + ", ip: " + nic.getIPv4Address());
            }

            final IPAddressVO ip = _ipAddressDao.findByIpAndSourceNetworkId(nic.getNetworkId(), nic.getIPv4Address());
            if (ip != null) {
                Transaction.execute(new TransactionCallbackNoReturn() {
                    @Override
                    public void doInTransactionWithoutResult(final TransactionStatus status) {
                        _ipAddrMgr.markIpAsUnavailable(ip.getId());
                        _ipAddressDao.unassignIpAddress(ip.getId());
                    }
                });
            }
            nic.deallocate();
        }
    }

};