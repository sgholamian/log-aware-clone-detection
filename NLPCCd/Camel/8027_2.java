//,temp,sample_1391.java,2,14,temp,sample_1390.java,2,11
//,3
public class xxx {
private BindingOperationInfo getBindingOperationInfo(Exchange ex) {
CxfEndpoint endpoint = (CxfEndpoint)this.getEndpoint();
BindingOperationInfo answer = null;
String lp = ex.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class);
if (lp == null) {


log.info("cxfproducer cannot find the from message header trying with defaultoperationname");
}
}

};