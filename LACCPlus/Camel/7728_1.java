//,temp,CamelJobExecutionListener.java,45,50,temp,CamelJobExecutionListener.java,38,43
//,2
public class xxx {
    @Override
    public void afterJob(JobExecution jobExecution) {
        LOG.debug("sending after job execution event [{}]...", jobExecution);
        producerTemplate.sendBodyAndHeader(endpointUri, jobExecution, EventType.HEADER_KEY, EventType.AFTER.name());
        LOG.debug("sent after job execution event");
    }

};