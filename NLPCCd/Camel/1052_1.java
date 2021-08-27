//,temp,sample_1388.java,2,13,temp,sample_5080.java,2,17
//,3
public class xxx {
private BindingOperationInfo prepareBindingOperation(Exchange camelExchange, org.apache.cxf.message.Exchange cxfExchange) {
BindingOperationInfo boi = getBindingOperationInfo(camelExchange);
ObjectHelper.notNull(boi, "BindingOperationInfo");
if (endpoint.getDataFormat() == DataFormat.PAYLOAD && boi.isUnwrapped()) {
boi = boi.getWrappedOperation();
cxfExchange.put(BindingOperationInfo.class, boi);
}
camelExchange.setProperty(BindingOperationInfo.class.getName(), boi);


log.info("set exchange property bindingoperationinfo");
}

};