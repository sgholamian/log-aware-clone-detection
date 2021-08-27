//,temp,FhirUpdateIT.java,167,186,temp,FhirUpdateIT.java,146,165
//,3
public class xxx {
    @Test
    public void testResourceBySearchUrl() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1998-04-29");
        assertNotEquals(date, patient.getBirthDate());
        this.patient.setBirthDate(date);
        String url = "Patient?" + Patient.SP_IDENTIFIER + '=' + URLEncoder.encode(this.patient.getId(), "UTF-8");
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is org.hl7.fhir.instance.model.api.IBaseResource
        headers.put("CamelFhir.resource", this.patient);
        // parameter type is String
        headers.put("CamelFhir.url", url);
        // parameter type is ca.uhn.fhir.rest.api.PreferReturnEnum
        headers.put("CamelFhir.preferReturn", PreferReturnEnum.REPRESENTATION);

        MethodOutcome result = requestBodyAndHeaders("direct://RESOURCE_BY_SEARCH_URL", null, headers);

        assertNotNull(result, "resource result");
        LOG.debug("resource: " + result);
        assertEquals(date, ((Patient) result.getResource()).getBirthDate(), "Birth date not updated!");
    }

};