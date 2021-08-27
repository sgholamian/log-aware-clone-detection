//,temp,sample_2145.java,2,10,temp,sample_4481.java,2,11
//,3
public class xxx {
protected void dump(RouteContainer context) throws Exception {
Marshaller marshaller = jaxbContext.createMarshaller();
marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
StringWriter buffer = new StringWriter();
marshaller.marshal(context, buffer);


log.info("created");
}

};