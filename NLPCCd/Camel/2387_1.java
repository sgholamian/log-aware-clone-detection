//,temp,sample_5381.java,2,10,temp,sample_5382.java,2,10
//,2
public class xxx {
public void onComplete(final Exchange exchange) {
try {
executor.submit(new RunCommand(successCommand, exchange)).get();
} catch (Exception e) {


log.info("could not run completion of exchange s");
}
}

};