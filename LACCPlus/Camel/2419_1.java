//,temp,FhirTransactionIT.java,90,106,temp,FhirTransactionIT.java,74,88
//,3
public class xxx {
    @Test
    public void testWithResourcesSummaryEnum() throws Exception {
        Patient oscar = new Patient().addName(new HumanName().addGiven("Oscar").setFamily("Peterson"));
        Patient bobbyHebb = new Patient().addName(new HumanName().addGiven("Bobby").setFamily("Hebb"));
        List<IBaseResource> patients = new ArrayList<>(2);
        patients.add(oscar);
        patients.add(bobbyHebb);
        final Map<String, Object> headers = new HashMap<>();
        headers.put(ExtraParameters.SUMMARY_ENUM.getHeaderName(), SummaryEnum.DATA);

        // using java.util.List message body for single parameter "resources"
        List<IBaseResource> result = requestBodyAndHeaders("direct://WITH_RESOURCES", patients, headers);

        assertNotNull(result, "withResources result");
        LOG.debug("withResources: " + result);
        assertEquals(2, result.size());
    }

};