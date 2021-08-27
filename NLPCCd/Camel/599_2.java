//,temp,sample_2525.java,2,11,temp,sample_1037.java,2,11
//,3
public class xxx {
public void testWithNonWebservice() {
try {
new ServiceInterfaceStrategy(Object.class, true);
fail("Should throw an exception for a class that is no webservice");
} catch (IllegalArgumentException e) {


log.info("caught expected message");
}
}

};