//,temp,FhirUpdateIT.java,86,104,temp,FhirUpdateIT.java,48,66
//,3
public class xxx {
    @Test
    public void testResourceStringId() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1998-04-29");
        assertNotEquals(date, patient.getBirthDate());
        this.patient.setBirthDate(date);
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is org.hl7.fhir.instance.model.api.IBaseResource
        headers.put("CamelFhir.resource", this.patient);
        // parameter type is org.hl7.fhir.instance.model.api.IIdType
        headers.put("CamelFhir.stringId", this.patient.getIdElement().getIdPart());
        // parameter type is ca.uhn.fhir.rest.api.PreferReturnEnum
        headers.put("CamelFhir.preferReturn", PreferReturnEnum.REPRESENTATION);

        MethodOutcome result = requestBodyAndHeaders("direct://RESOURCE_WITH_STRING_ID", null, headers);

        assertNotNull(result, "resource result");
        LOG.debug("resource: " + result);
        assertEquals(date, ((Patient) result.getResource()).getBirthDate(), "Birth date not updated!");
    }

};