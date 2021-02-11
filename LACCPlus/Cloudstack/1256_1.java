//,temp,SyncQueueManagerImpl.java,207,233,temp,SyncQueueManagerImpl.java,178,205
//,3
public class xxx {
    @Override
    @DB
    public void returnItem(final long queueItemId) {
        s_logger.info("Returning queue item " + queueItemId + " back to queue for second try in case of DB deadlock");
        try {
            Transaction.execute(new TransactionCallbackNoReturn() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    SyncQueueItemVO itemVO = _syncQueueItemDao.findById(queueItemId);
                    if(itemVO != null) {
                        SyncQueueVO queueVO = _syncQueueDao.findById(itemVO.getQueueId());

                        itemVO.setLastProcessMsid(null);
                        itemVO.setLastProcessNumber(null);
                        itemVO.setLastProcessTime(null);
                        _syncQueueItemDao.update(queueItemId, itemVO);

                        queueVO.setQueueSize(queueVO.getQueueSize() - 1);
                        queueVO.setLastUpdated(DateUtil.currentGMTTime());
                        _syncQueueDao.update(queueVO.getId(), queueVO);
                    }
                }
            });
        } catch (Exception e) {
            s_logger.error("Unexpected exception: ", e);
        }
    }

};