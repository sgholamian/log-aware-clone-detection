//,temp,sample_280.java,2,18,temp,sample_278.java,2,19
//,3
public class xxx {
public void dummy_method(){
try {
initializeHelper(dsProps);
return;
} catch (Exception e){
numTries--;
boolean retriable = isRetriableException(e);
if ((numTries > 0) && retriable){
try {
Thread.sleep(retryInterval);
} catch (InterruptedException ie) {


log.info("interrupted while sleeping before retrying");
}
}
}
}

};