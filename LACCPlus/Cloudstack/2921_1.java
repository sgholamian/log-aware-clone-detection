//,temp,UsageIPAddressDaoImpl.java,57,82,temp,UsageLoadBalancerPolicyDaoImpl.java,73,97
//,2
public class xxx {
    @Override
    public void update(UsageIPAddressVO usage) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        try {
            txn.start();
            if (usage.getReleased() != null) {
                try(PreparedStatement pstmt = txn.prepareStatement(UPDATE_RELEASED);) {
                    if (pstmt != null) {
                        pstmt.setString(1, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), usage.getReleased()));
                        pstmt.setLong(2, usage.getAccountId());
                        pstmt.setString(3, usage.getAddress());
                        pstmt.executeUpdate();
                    }
                }catch(SQLException e)
                {
                   throw new CloudException("update:Exception:"+e.getMessage(), e);
                }
            }
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.error("Error updating usageIPAddressVO:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
    }

};