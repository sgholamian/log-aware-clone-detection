//,temp,DiscountGatewayIT.java,40,47,temp,JaxpTest.java,75,82
//,3
public class xxx {
    @Test
    public void testStreamSourceToDomSource() throws Exception {
        StreamSource streamSource = new StreamSource(new StringReader("<hello>world!</hello>"));
        DOMSource domSource = converter.convertTo(DOMSource.class, streamSource);
        assertNotNull(domSource, "Could not convert to a DOMSource!");

        LOG.debug("Found document: " + domSource);
    }

};