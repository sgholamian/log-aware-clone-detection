//,temp,UserVmDaoImpl.java,383,421,temp,VMTemplateHostDaoImpl.java,256,281
//,3
public class xxx {
    @Override
    public List<Long> listPodIdsHavingVmsforAccount(long zoneId, long accountId) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        List<Long> result = new ArrayList<Long>();
        String sql = LIST_PODS_HAVING_VMS_FOR_ACCOUNT;

        try(PreparedStatement pstmt = txn.prepareStatement(sql)) {
            pstmt.setLong(1, zoneId);
            pstmt.setLong(2, accountId);
            try(ResultSet rs = pstmt.executeQuery();)
            {
                while (rs.next()) {
                    result.add(rs.getLong(1));
                }
            }
            catch (Exception e) {
                s_logger.error("listPodIdsHavingVmsforAccount:Exception: " +  e.getMessage());
                throw new CloudRuntimeException("listPodIdsHavingVmsforAccount:Exception: " + e.getMessage(), e);
            }
            txn.commit();
            return result;
        } catch (Exception e) {
            s_logger.error("listPodIdsHavingVmsforAccount:Exception : " +  e.getMessage());
            throw new CloudRuntimeException("listPodIdsHavingVmsforAccount:Exception: " + e.getMessage(), e);
        }
        finally {
            try{
                if (txn != null)
                {
                    txn.close();
                }
            }
            catch (Exception e)
            {
                s_logger.error("listPodIdsHavingVmsforAccount:Exception:" + e.getMessage());
            }
        }

    }

};