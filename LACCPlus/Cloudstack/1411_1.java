//,temp,SyncQueueManagerImpl.java,130,176,temp,SyncQueueManagerImpl.java,78,128
//,3
public class xxx {
    @Override
    @DB
    public List<SyncQueueItemVO> dequeueFromAny(final Long msid, final int maxItems) {

        final List<SyncQueueItemVO> resultList = new ArrayList<SyncQueueItemVO>();

        try {
            Transaction.execute(new TransactionCallbackNoReturn() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    List<SyncQueueItemVO> l = _syncQueueItemDao.getNextQueueItems(maxItems);
                    if(l != null && l.size() > 0) {
                        for(SyncQueueItemVO item : l) {
                            SyncQueueVO queueVO = _syncQueueDao.findById(item.getQueueId());
                            SyncQueueItemVO itemVO = _syncQueueItemDao.findById(item.getId());
                            if(queueReadyToProcess(queueVO) && itemVO != null && itemVO.getLastProcessNumber() == null) {
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
                                _syncQueueItemDao.update(item.getId(), itemVO);

                                resultList.add(itemVO);
                            }
                        }
                    }
                }
            });

            return resultList;
        } catch (Exception e) {
            s_logger.error("Unexpected exception: ", e);
        }

        return null;
    }

};