//,temp,CamelJobExecutionListener.java,45,50,temp,CamelJobExecutionListener.java,38,43
//,2
public class xxx {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOG.debug("sending before job execution event [{}]...", jobExecution);
        producerTemplate.sendBodyAndHeader(endpointUri, jobExecution, EventType.HEADER_KEY, EventType.BEFORE.name());
        LOG.debug("sent before job execution event");
    }

};