//,temp,sample_2318.java,2,12,temp,sample_2317.java,2,11
//,2
public class xxx {
protected void doStart() throws Exception {
if (isEndpointTransacted()) {
throw new IllegalArgumentException("InOut exchange pattern is incompatible with transacted=true as it cuases a deadlock. Please use transacted=false or InOnly exchange pattern.");
}
if (ObjectHelper.isEmpty(getNamedReplyTo())) {


log.info("no reply to destination is defined using temporary destinations");
}
}

};