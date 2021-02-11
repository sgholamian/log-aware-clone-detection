//,temp,ManagementServerImpl.java,3903,3919,temp,ManagementServerImpl.java,3846,3864
//,3
public class xxx {
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

};