//,temp,sample_8172.java,2,13,temp,sample_8173.java,2,15
//,3
public class xxx {
public void initialize(Server server, Bus bus) {
server.getEndpoint().put("org.apache.cxf.binding.soap.addNamespaceContext", "true");
server.getEndpoint().getBinding().getInInterceptors().add(new ConfigureDocLitWrapperInterceptor(true));
if (server.getEndpoint().getBinding() instanceof SoapBinding) {
server.getEndpoint().getBinding().getOutInterceptors().add(new SetSoapVersionInterceptor());
}
removeInterceptor(server.getEndpoint().getInInterceptors(), HolderInInterceptor.class);
removeInterceptor(server.getEndpoint().getOutInterceptors(), HolderOutInterceptor.class);
removeInterceptor(server.getEndpoint().getBinding().getInInterceptors(), SoapHeaderInterceptor.class);
resetPartTypes(server.getEndpoint().getBinding());


log.info("initialized cxf server in payload mode with allow streaming");
}

};