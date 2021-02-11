//,temp,SyncQueueManagerImpl.java,130,176,temp,SyncQueueManagerImpl.java,78,128
//,3
public class xxx {
    @Override
    @DB
    public SyncQueueItemVO dequeueFromOne(final long queueId, final Long msid) {
        try {
            return Transaction.execute(new TransactionCallback<SyncQueueItemVO>() {
                @Override
                public SyncQueueItemVO doInTransaction(TransactionStatus status) {
                    SyncQueueVO queueVO = _syncQueueDao.findById(queueId);
                    if(queueVO == null) {
                        s_logger.error("Sync queue(id: " + queueId + ") does not exist");
                        return null;
                    }

                    if (queueReadyToProcess(queueVO)) {
                        SyncQueueItemVO itemVO = _syncQueueItemDao.getNextQueueItem(queueVO.getId());
                        if (itemVO != null) {
                            Long processNumber = queueVO.getLastProcessNumber();
                            if (processNumber == null)
                                processNumber = new Long(1);
                            else
                                processNumber = processNumber + 1;
                            Date dt = DateUtil.currentGMTTime();
                            queueVO.setLastProcessNumber(processNumber);
                            queueVO.setLastUpdated(dt);
                            queueVO.setQueueSize(queueVO.getQueueSize() + 1);
                            _syncQueueDao.update(queueVO.getId(), queueVO);

                            itemVO.setLastProcessMsid(msid);
                            itemVO.setLastProcessNumber(processNumber);
                            itemVO.setLastProcessTime(dt);
                            _syncQueueItemDao.update(itemVO.getId(), itemVO);

                            return itemVO;
                        } else {
                            if (s_logger.isDebugEnabled())
                                s_logger.debug("Sync queue (" + queueId + ") is currently empty");
                        }
                    } else {
                        if (s_logger.isDebugEnabled())
                            s_logger.debug("There is a pending process in sync queue(id: " + queueId + ")");
                    }

                    return null;
                }
            });
        } catch (Exception e) {
            s_logger.error("Unexpected exception: ", e);
        }

        return null;
    }

};