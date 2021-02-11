//,temp,PublicNetworkGuru.java,199,221,temp,DirectNetworkGuru.java,364,395
//,3
public class xxx {
    @Override
    @DB
    public boolean trash(Network network, NetworkOffering offering) {
        //Have to remove all placeholder nics
        try {
            long id = network.getId();
            final List<NicVO> nics = _nicDao.listPlaceholderNicsByNetworkId(id);
            if (nics != null) {
                Transaction.execute(new TransactionCallbackNoReturn() {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        for (Nic nic : nics) {
                            if (nic.getIPv4Address() != null) {
                                s_logger.debug("Releasing ip " + nic.getIPv4Address() + " of placeholder nic " + nic);
                                IPAddressVO ip = _ipAddressDao.findByIpAndSourceNetworkId(nic.getNetworkId(), nic.getIPv4Address());
                                if (ip != null) {
                                    _ipAddrMgr.markIpAsUnavailable(ip.getId());
                                    _ipAddressDao.unassignIpAddress(ip.getId());
                                    s_logger.debug("Removing placeholder nic " + nic);
                                    _nicDao.remove(nic.getId());
                                }
                            }
                        }
                    }
                });
            }
            return true;
        }catch (Exception e) {
            s_logger.error("trash. Exception:" + e.getMessage());
            throw new CloudRuntimeException("trash. Exception:" + e.getMessage(),e);
        }
    }

};