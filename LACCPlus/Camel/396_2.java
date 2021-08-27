//,temp,JaxpTest.java,67,73,temp,FhirMetaIT.java,105,111
//,3
public class xxx {
    @Test
    public void testGetFromServer() throws Exception {
        // using Class message body for single parameter "metaType"
        IBaseMetaType result = requestBody("direct://GET_FROM_SERVER", Meta.class);
        assertNotNull(result, "getFromServer result");
        LOG.debug("getFromServer: " + result);
    }

};