//,temp,ManagementServerImpl.java,3886,3922,temp,ManagementServerImpl.java,3841,3867
//,3
public class xxx {
    private boolean updateHostsInCluster(final UpdateHostPasswordCmd command) {
        // get all the hosts in this cluster
        final List<HostVO> hosts = _resourceMgr.listAllHostsInCluster(command.getClusterId());

        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(final TransactionStatus status) {
                for (final HostVO h : hosts) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Changing password for host name = " + h.getName());
                    }
                    // update password for this host
                    final DetailVO nv = _detailsDao.findDetail(h.getId(), ApiConstants.USERNAME);
                    if (nv.getValue().equals(command.getUsername())) {
                        final DetailVO nvp = _detailsDao.findDetail(h.getId(), ApiConstants.PASSWORD);
                        nvp.setValue(DBEncryptionUtil.encrypt(command.getPassword()));
                        _detailsDao.persist(nvp);
                    } else {
                        // if one host in the cluster has diff username then
                        // rollback to maintain consistency
                        throw new InvalidParameterValueException("The username is not same for all hosts, please modify passwords for individual hosts.");
                    }
                }
            }
        });
        return true;
    }

};