//,temp,FhirPatchIT.java,97,113,temp,BoxFoldersManagerIT.java,135,152
//,3
public class xxx {
    @Test
    @Disabled(value = "https://github.com/jamesagnew/hapi-fhir/issues/955")
    public void testPatchByUrl() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelFhir.patchBody", PATCH);
        // parameter type is String
        headers.put("CamelFhir.url", "Patient?given=Vincent&family=Freeman");
        // parameter type is ca.uhn.fhir.rest.api.PreferReturnEnum
        headers.put("CamelFhir.preferReturn", null);

        MethodOutcome result = requestBodyAndHeaders("direct://PATCH_BY_URL", null, headers);

        assertNotNull(result, "patchByUrl result");
        LOG.debug("patchByUrl: " + result);
        assertActive(result);
    }

};