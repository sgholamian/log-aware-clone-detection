//,temp,FhirTransactionIT.java,90,106,temp,FhirTransactionIT.java,74,88
//,3
public class xxx {
    @Test
    public void testWithResources() throws Exception {
        Patient oscar = new Patient().addName(new HumanName().addGiven("Oscar").setFamily("Peterson"));
        Patient bobbyHebb = new Patient().addName(new HumanName().addGiven("Bobby").setFamily("Hebb"));
        List<IBaseResource> patients = new ArrayList<>(2);
        patients.add(oscar);
        patients.add(bobbyHebb);

        // using java.util.List message body for single parameter "resources"
        List<IBaseResource> result = requestBody("direct://WITH_RESOURCES", patients);

        assertNotNull(result, "withResources result");
        LOG.debug("withResources: " + result);
        assertEquals(2, result.size());
    }

};