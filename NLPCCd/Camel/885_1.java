//,temp,sample_8172.java,2,13,temp,sample_8173.java,2,15
//,3
public class xxx {
public void initialize(Client client, Bus bus) {
client.getEndpoint().put("org.apache.cxf.binding.soap.addNamespaceContext", "true");
removeFaultInInterceptorFromClient(client);
removeInterceptor(client.getEndpoint().getInInterceptors(), HolderInInterceptor.class);
removeInterceptor(client.getEndpoint().getOutInterceptors(), HolderOutInterceptor.class);
removeInterceptor(client.getEndpoint().getBinding().getInInterceptors(), SoapHeaderInterceptor.class);
client.getEndpoint().getBinding().getInInterceptors().add(new ConfigureDocLitWrapperInterceptor(true));
resetPartTypes(client.getEndpoint().getBinding());


log.info("initialized cxf client in payload mode with allow streaming");
}

};