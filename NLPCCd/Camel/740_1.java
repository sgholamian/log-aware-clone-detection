//,temp,sample_5293.java,2,10,temp,sample_496.java,2,11
//,3
public class xxx {
public void assertIsSatisfied(long timeoutForEmptyEndpoints) throws InterruptedException {
doAssertIsSatisfied(timeoutForEmptyEndpoints);
if (assertPeriod > 0) {
Thread.sleep(assertPeriod);


log.info("re asserting is satisfied after millis");
}
}

};