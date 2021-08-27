//,temp,JaxpTest.java,67,73,temp,FhirMetaIT.java,105,111
//,3
public class xxx {
    @Test
    public void testConvertToSource() throws Exception {
        Source source = converter.convertTo(Source.class, "<hello>world!</hello>");
        assertNotNull(source);

        LOG.debug("Found document: " + source);
    }

};