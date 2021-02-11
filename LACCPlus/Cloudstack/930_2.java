//,temp,OvsTunnelManagerImpl.java,189,205,temp,SecurityGroupManagerImpl.java,816,840
//,3
public class xxx {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                SecurityGroupVO groupHandle = null;

                try {
                    // acquire lock on parent group (preserving this logic)
                    groupHandle = _securityGroupDao.acquireInLockTable(rule.getSecurityGroupId());
                    if (groupHandle == null) {
                        s_logger.warn("Could not acquire lock on security group id: " + rule.getSecurityGroupId());
                        return false;
                    }

                    _securityGroupRuleDao.remove(id);
                    s_logger.debug("revokeSecurityGroupRule succeeded for security rule id: " + id);

                    return true;
                } catch (Exception e) {
                    s_logger.warn("Exception caught when deleting security rules ", e);
                    throw new CloudRuntimeException("Exception caught when deleting security rules", e);
                } finally {
                    if (groupHandle != null) {
                        _securityGroupDao.releaseFromLockTable(groupHandle.getId());
                    }
                }
            }

};