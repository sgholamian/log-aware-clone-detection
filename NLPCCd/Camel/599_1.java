//,temp,sample_2525.java,2,11,temp,sample_1037.java,2,11
//,3
public class xxx {
public void testSendToNonExistingEndpoint() throws Exception {
try {
template.sendBody("thisUriDoesNotExist", "<hello>world!</hello>");
fail("Should have failed to send this message!");
} catch (NoSuchEndpointException e) {


log.info("caught expected exception");
}
}

};