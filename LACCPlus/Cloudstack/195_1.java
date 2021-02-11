//,temp,AutoScaleManagerImpl.java,851,868,temp,AutoScaleManagerImpl.java,594,610
//,3
public class xxx {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                boolean success = _autoScaleVmGroupDao.remove(id);

                if (!success) {
                    s_logger.warn("Failed to remove AutoScale Group db object");
                    return false;
                }

                success = _autoScaleVmGroupPolicyMapDao.removeByGroupId(id);
                if (!success) {
                    s_logger.warn("Failed to remove AutoScale Group Policy mappings");
                    return false;
                }

                s_logger.info("Successfully deleted autoscale vm group id : " + id);
                return success; // Successfull
            }

};