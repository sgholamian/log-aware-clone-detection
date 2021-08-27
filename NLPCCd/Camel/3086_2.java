//,temp,sample_3704.java,2,14,temp,sample_5067.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (!run) {
return;
}
Exchange exchange = createExchange(jobExecutionContext);
try {
balancer.process(exchange);
if (exchange.getException() != null) {
throw new JobExecutionException(exchange.getException());
}
} catch (Exception e) {


log.info("error processing exchange");
}
}

};