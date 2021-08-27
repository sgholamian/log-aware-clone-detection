//,temp,sample_7862.java,2,11,temp,sample_2620.java,2,11
//,3
public class xxx {
protected void setUp() throws Exception {
try {
super.setUp();
fail("Should have failed to create this route!");
} catch (FailedToCreateRouteException e) {


log.info("caught expected exception");
}
}

};