//,temp,sample_7494.java,2,9,temp,sample_4920.java,2,9
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
BindingOperationInfo boi = (BindingOperationInfo)exchange.getProperty(BindingOperationInfo.class.getName());
if (boi != null) {


log.info("boi isunwrapped");
}
}

};