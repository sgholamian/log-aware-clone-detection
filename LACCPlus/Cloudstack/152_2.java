//,temp,AsyncJobManagerImpl.java,370,390,temp,AsyncJobManagerImpl.java,340,368
//,3
public class xxx {
    @Override
    @DB
    public void updateAsyncJobStatus(final long jobId, final int processStatus, final String resultObject) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Update async-job progress, job-" + jobId + ", processStatus: " + processStatus + ", result: " + resultObject);
        }

        final AsyncJobVO job = _jobDao.findById(jobId);
        if (job == null) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("job-" + jobId + " no longer exists, we just log progress info here. progress status: " + processStatus);
            }

            return;
        }

        publishOnEventBus(job, "update");
        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                job.setProcessStatus(processStatus);
                if (resultObject != null) {
                    job.setResult(resultObject);
                }
                job.setLastUpdated(DateUtil.currentGMTTime());
                _jobDao.update(jobId, job);
            }
        });
    }

};