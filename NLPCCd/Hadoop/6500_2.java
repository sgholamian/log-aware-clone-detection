//,temp,sample_8691.java,2,11,temp,sample_8854.java,2,11
//,3
public class xxx {
public void testAllocateRequestWithoutRegistering() throws Exception {
try {
allocate(1);
Assert .fail("The request to allocate application master should have failed");
} catch (Throwable ex) {


log.info("allocaterequest failed as expected because am was not registered");
}
}

};