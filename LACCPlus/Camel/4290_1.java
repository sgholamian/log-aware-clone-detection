//,temp,FhirMetaIT.java,65,88,temp,FhirMetaIT.java,45,63
//,3
public class xxx {
    @Test
    public void testDelete() throws Exception {
        //assert no meta
        Meta meta = fhirClient.meta().get(Meta.class).fromResource(this.patient.getIdElement()).execute();
        assertEquals(0, meta.getTag().size());
        Meta inMeta = new Meta();
        inMeta.addTag().setSystem("urn:system1").setCode("urn:code1");
        // add meta
        meta = fhirClient.meta().add().onResource(this.patient.getIdElement()).meta(inMeta).execute();
        assertEquals(1, meta.getTag().size());

        //delete meta
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is org.hl7.fhir.instance.model.api.IBaseMetaType
        headers.put("CamelFhir.meta", meta);
        // parameter type is org.hl7.fhir.instance.model.api.IIdType
        headers.put("CamelFhir.id", this.patient.getIdElement());

        IBaseMetaType result = requestBodyAndHeaders("direct://DELETE", null, headers);

        LOG.debug("delete: " + result);
        assertNotNull(result, "delete result");
        assertEquals(0, result.getTag().size());
    }

};