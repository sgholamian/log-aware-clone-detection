//,temp,sample_2459.java,2,13,temp,sample_2339.java,2,10
//,3
public class xxx {
public Metadata parse(Element element, ParserContext context) {
Metadata answer = null;
String s = element.getLocalName();
if ("conduit".equals(s)) {
answer = new CamelConduitDefinitionParser().parse(element, context);
}
if ("destination".equals(s)) {


log.info("parsing the detination element");
}
}

};