//,temp,sample_5381.java,2,10,temp,sample_5382.java,2,10
//,2
public class xxx {
public void onFailure(final Exchange exchange) {
try {
executor.submit(new RunCommand(failureCommand, exchange)).get();
} catch (Exception e) {


log.info("s could not run failure of exchange s");
}
}

};