//,temp,sample_2458.java,2,10,temp,sample_2340.java,2,13
//,3
public class xxx {
public Metadata parse(Element element, ParserContext context) {
Metadata answer = null;
String s = element.getLocalName();
if ("cxfEndpoint".equals(s)) {
answer = new EndpointDefinitionParser().parse(element, context);
}
if ("rsClient".equals(s)) {


log.info("parsing the rsclient element");
}
}

};