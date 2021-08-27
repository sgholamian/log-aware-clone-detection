//,temp,XmlTestSupport.java,45,50,temp,XmlTestSupport.java,38,43
//,2
public class xxx {
    protected RouteContainer assertParseAsJaxb(String uri) throws JAXBException {
        Object value = parseUri(uri);
        RouteContainer context = assertIsInstanceOf(RouteContainer.class, value);
        log.info("Found: " + context);
        return context;
    }

};