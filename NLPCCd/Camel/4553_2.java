//,temp,sample_4442.java,2,7,temp,sample_4440.java,2,7
//,2
public class xxx {
public void beforeJob(JobExecution jobExecution) {
producerTemplate.sendBodyAndHeader(endpointUri, jobExecution, EventType.HEADER_KEY, EventType.BEFORE.name());


log.info("sent before job execution event");
}

};