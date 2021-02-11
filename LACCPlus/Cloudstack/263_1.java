//,temp,StorageNetworkManagerImpl.java,282,302,temp,UserVmManagerImpl.java,2858,2883
//,3
public class xxx {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                StorageNetworkIpRangeVO range = null;
                try {
                    range = _sNwIpRangeDao.acquireInLockTable(rangeId);
                    if (range == null) {
                        String msg = "Unable to acquire lock on storage network ip range id=" + rangeId + ", delete failed";
                        s_logger.warn(msg);
                        throw new CloudRuntimeException(msg);
                    }
                    /*
                     * entries in op_dc_storage_network_ip_address will be deleted automatically due to
                     * fk_storage_ip_address__range_id constraint key
                     */
                    _sNwIpRangeDao.remove(rangeId);
                } finally {
                    if (range != null) {
                        _sNwIpRangeDao.releaseFromLockTable(rangeId);
                    }
                }
            }

};