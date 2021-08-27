//,temp,sample_5297.java,2,12,temp,sample_5296.java,2,12
//,3
public class xxx {
public void assertIsNotSatisfied() throws InterruptedException {
boolean failed = false;
try {
assertIsSatisfied();
failed = true;
} catch (AssertionError e) {


log.info("caught expected failure");
}
}

};