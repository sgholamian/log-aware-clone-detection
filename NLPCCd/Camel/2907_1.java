//,temp,sample_7862.java,2,11,temp,sample_2620.java,2,11
//,3
public class xxx {
public void testAmbiguousMethodCallFails() throws Exception {
try {
Object value = template.requestBody("bean:myBean", body);
fail("We should have failed to invoke an ambiguous method but instead got: " + value);
} catch (Exception e) {


log.info("caught expected failure");
}
}

};