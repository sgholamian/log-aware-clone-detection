//,temp,sample_4442.java,2,7,temp,sample_4440.java,2,7
//,2
public class xxx {
public void afterJob(JobExecution jobExecution) {
producerTemplate.sendBodyAndHeader(endpointUri, jobExecution, EventType.HEADER_KEY, EventType.AFTER.name());


log.info("sent after job execution event");
}

};