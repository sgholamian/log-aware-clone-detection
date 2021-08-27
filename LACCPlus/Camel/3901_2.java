//,temp,FhirLoadPageIT.java,74,87,temp,FhirLoadPageIT.java,52,72
//,3
public class xxx {
    @Test
    public void testByUrl() throws Exception {
        String url = "Patient?_count=2";
        Bundle bundle = this.fhirClient.search()
                .byUrl(url)
                .returnBundle(Bundle.class).execute();
        assertNotNull(bundle.getLink(IBaseBundle.LINK_NEXT));

        String nextPageLink = bundle.getLink("next").getUrl();

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelFhir.url", nextPageLink);
        // parameter type is Class
        headers.put("CamelFhir.returnType", Bundle.class);

        IBaseBundle result = requestBodyAndHeaders("direct://BY_URL", null, headers);

        LOG.debug("byUrl: " + result);
        assertNotNull(result, "byUrl result");
    }

};