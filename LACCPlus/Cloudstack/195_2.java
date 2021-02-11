//,temp,AutoScaleManagerImpl.java,851,868,temp,AutoScaleManagerImpl.java,594,610
//,3
public class xxx {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                boolean success = true;
                success = _autoScalePolicyDao.remove(id);
                if (!success) {
                    s_logger.warn("Failed to remove AutoScale Policy db object");
                    return false;
                }
                success = _autoScalePolicyConditionMapDao.removeByAutoScalePolicyId(id);
                if (!success) {
                    s_logger.warn("Failed to remove AutoScale Policy Condition mappings");
                    return false;
                }
                s_logger.info("Successfully deleted autoscale policy id : " + id);

                return success;
            }

};