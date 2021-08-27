//,temp,FhirLoadPageIT.java,74,87,temp,FhirLoadPageIT.java,52,72
//,3
public class xxx {
    @Test
    public void testNext() throws Exception {
        String url = "Patient?_count=2";
        Bundle bundle = this.fhirClient.search()
                .byUrl(url)
                .returnBundle(Bundle.class).execute();
        assertNotNull(bundle.getLink(IBaseBundle.LINK_NEXT));

        // using org.hl7.fhir.instance.model.api.IBaseBundle message body for single parameter "bundle"
        Bundle result = requestBody("direct://NEXT", bundle);

        assertNotNull(result, "next result");
        LOG.debug("next: " + result);
    }

};