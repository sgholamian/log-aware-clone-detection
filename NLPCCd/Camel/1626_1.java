//,temp,sample_3085.java,2,17,temp,sample_3086.java,2,17
//,3
public class xxx {
public void dummy_method(){
setProperty(factory, XMLInputFactory.IS_NAMESPACE_AWARE, nsAware);
setProperty(factory, XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
setProperty(factory, XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
setProperty(factory, XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
factory.setXMLResolver(new XMLResolver() {
public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
throw new XMLStreamException("Reading external entities is disabled");
}
});
if (isWoodstox(factory)) {


log.info("created woodstox xmlinputfactory");
}
}

};