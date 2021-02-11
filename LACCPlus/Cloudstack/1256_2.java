//,temp,SyncQueueManagerImpl.java,207,233,temp,SyncQueueManagerImpl.java,178,205
//,3
public class xxx {
    @Override
    @DB
    public void purgeItem(final long queueItemId) {
        try {
            Transaction.execute(new TransactionCallbackNoReturn() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    SyncQueueItemVO itemVO = _syncQueueItemDao.findById(queueItemId);
                    if(itemVO != null) {
                        SyncQueueVO queueVO = _syncQueueDao.findById(itemVO.getQueueId());

                        _syncQueueItemDao.expunge(itemVO.getId());

                        // if item is active, reset queue information
                        if (itemVO.getLastProcessMsid() != null) {
                            queueVO.setLastUpdated(DateUtil.currentGMTTime());
                            // decrement the count
                            assert (queueVO.getQueueSize() > 0) : "Count reduce happens when it's already <= 0!";
                            queueVO.setQueueSize(queueVO.getQueueSize() - 1);
                            _syncQueueDao.update(queueVO.getId(), queueVO);
                        }
                    }
                }
            });
        } catch (Exception e) {
            s_logger.error("Unexpected exception: ", e);
        }
    }

};