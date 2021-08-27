//,temp,FhirMetaIT.java,65,88,temp,FhirMetaIT.java,45,63
//,3
public class xxx {
    @Test
    public void testAdd() throws Exception {
        //assert no meta
        Meta meta = fhirClient.meta().get(Meta.class).fromResource(this.patient.getIdElement()).execute();
        assertEquals(0, meta.getTag().size());
        Meta inMeta = new Meta();
        inMeta.addTag().setSystem("urn:system1").setCode("urn:code1");
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is org.hl7.fhir.instance.model.api.IBaseMetaType
        headers.put("CamelFhir.meta", inMeta);
        // parameter type is org.hl7.fhir.instance.model.api.IIdType
        headers.put("CamelFhir.id", this.patient.getIdElement());

        IBaseMetaType result = requestBodyAndHeaders("direct://ADD", null, headers);

        LOG.debug("add: " + result);
        assertNotNull(result, "add result");
        assertEquals(1, result.getTag().size());
    }

};