//,temp,AsyncJobManagerImpl.java,370,390,temp,AsyncJobManagerImpl.java,340,368
//,3
public class xxx {
    @Override
    @DB
    public void updateAsyncJobAttachment(final long jobId, final String instanceType, final Long instanceId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Update async-job attachment, job-" + jobId + ", instanceType: " + instanceType + ", instanceId: " + instanceId);
        }

        final AsyncJobVO job = _jobDao.findById(jobId);
        publishOnEventBus(job, "update");

        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                AsyncJobVO job = _jobDao.createForUpdate();
                job.setInstanceType(instanceType);
                job.setInstanceId(instanceId);
                job.setLastUpdated(DateUtil.currentGMTTime());
                _jobDao.update(jobId, job);
            }
        });
    }

};