//,temp,FhirLoadPageIT.java,108,127,temp,FhirLoadPageIT.java,89,106
//,3
public class xxx {
    @Test
    public void testPreviousWithEncodingEnum() throws Exception {
        String url = "Patient?_count=2";
        Bundle bundle = this.fhirClient.search()
                .byUrl(url)
                .returnBundle(Bundle.class).execute();
        assertNotNull(bundle.getLink(IBaseBundle.LINK_NEXT));

        String nextPageLink = bundle.getLink("next").getUrl();
        bundle = this.fhirClient.loadPage().byUrl(nextPageLink).andReturnBundle(Bundle.class).execute();
        assertNotNull(bundle.getLink(IBaseBundle.LINK_PREV));
        Map<String, Object> headers = new HashMap<>();
        headers.put(ExtraParameters.ENCODING_ENUM.getHeaderName(), EncodingEnum.XML);

        // using org.hl7.fhir.instance.model.api.IBaseBundle message body for single parameter "bundle"
        Bundle result = requestBodyAndHeaders("direct://PREVIOUS", bundle, headers);

        LOG.debug("previous: " + result);
        assertNotNull(result, "previous result");
    }

};