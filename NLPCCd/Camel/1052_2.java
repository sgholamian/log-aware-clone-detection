//,temp,sample_1388.java,2,13,temp,sample_5080.java,2,17
//,3
public class xxx {
public void dummy_method(){
org.apache.camel.Exchange camelExchange = endpoint.createExchange();
cxfExchange.put(org.apache.camel.Exchange.class, camelExchange);
DataFormat dataFormat = endpoint.getDataFormat();
BindingOperationInfo boi = cxfExchange.getBindingOperationInfo();
if (boi != null && dataFormat == DataFormat.PAYLOAD && boi.isUnwrapped()) {
boi = boi.getWrappedOperation();
cxfExchange.put(BindingOperationInfo.class, boi);
}
if (boi != null) {
camelExchange.setProperty(BindingOperationInfo.class.getName(), boi);


log.info("set exchange property bindingoperationinfo");
}
}

};