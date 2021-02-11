//,temp,UsageSecurityGroupDaoImpl.java,56,81,temp,UsagePortForwardingRuleDaoImpl.java,73,97
//,3
public class xxx {
    @Override
    public void update(UsagePortForwardingRuleVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            if (usage.getDeleted() != null) {
                try(PreparedStatement pstmt = txn.prepareStatement(UPDATE_DELETED);) {
                    if (pstmt != null) {
                        pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getDeleted()));
                        pstmt.setLong(2, usage.getAccountId());
                        pstmt.setLong(3, usage.getId());
                        pstmt.executeUpdate();
                    }
                }catch (SQLException e) {
                    throw new CloudException("Error updating UsagePortForwardingRuleVO:"+e.getMessage(), e);
                }
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error updating UsagePortForwardingRuleVO:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};