//,temp,XmlTestSupport.java,45,50,temp,XmlTestSupport.java,38,43
//,2
public class xxx {
    protected RestContainer assertParseRestAsJaxb(String uri) throws JAXBException {
        Object value = parseUri(uri);
        RestContainer context = assertIsInstanceOf(RestContainer.class, value);
        log.info("Found: " + context);
        return context;
    }

};