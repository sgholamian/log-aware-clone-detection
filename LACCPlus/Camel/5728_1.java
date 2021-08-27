//,temp,FhirTransactionIT.java,61,72,temp,FhirTransactionIT.java,51,59
//,3
public class xxx {
    @Test
    public void testWithStringBundle() throws Exception {
        Bundle transactionBundle = createTransactionBundle();
        String stringBundle = fhirContext.newJsonParser().encodeResourceToString(transactionBundle);

        // using String message body for single parameter "sBundle"
        final String result = requestBody("direct://WITH_STRING_BUNDLE", stringBundle);

        assertNotNull(result, "withBundle result");
        assertTrue(result.contains("Bundle"));
        LOG.debug("withBundle: " + result);
    }

};