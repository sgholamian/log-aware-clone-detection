//,temp,ManagementServerImpl.java,3886,3922,temp,ManagementServerImpl.java,3841,3867
//,3
public class xxx {
    @Override
    @DB
    public boolean updateHostPassword(final UpdateHostPasswordCmd cmd) {
        if (cmd.getHostId() == null) {
            throw new InvalidParameterValueException("You should provide an host id.");
        }

        final HostVO host = _hostDao.findById(cmd.getHostId());

        if (host.getHypervisorType() == HypervisorType.XenServer) {
            throw new InvalidParameterValueException("Single host update is not supported by XenServer hypervisors. Please try again informing the Cluster ID.");
        }

        if (!supportedHypervisors.contains(host.getHypervisorType())) {
            throw new InvalidParameterValueException("This operation is not supported for this hypervisor type");
        }
        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(final TransactionStatus status) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Changing password for host name = " + host.getName());
                }
                // update password for this host
                final DetailVO nv = _detailsDao.findDetail(host.getId(), ApiConstants.USERNAME);
                if (nv.getValue().equals(cmd.getUsername())) {
                    final DetailVO nvp = _detailsDao.findDetail(host.getId(), ApiConstants.PASSWORD);
                    nvp.setValue(DBEncryptionUtil.encrypt(cmd.getPassword()));
                    _detailsDao.persist(nvp);
                } else {
                    // if one host in the cluster has diff username then
                    // rollback to maintain consistency
                    throw new InvalidParameterValueException("The username is not same for the hosts..");
                }
            }
        });
        return true;
    }

};