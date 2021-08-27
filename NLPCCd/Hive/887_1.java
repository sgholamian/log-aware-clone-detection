//,temp,sample_1701.java,2,17,temp,sample_5501.java,2,17
//,3
public class xxx {
public void dummy_method(){
org.apache.thrift.TBase msg;
fire_listener_event_result result = new fire_listener_event_result();
{
msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
}
try {
fcall.sendResponse(fb,msg,msgType,seqid);
return;
} catch (Exception ex) {


log.info("exception writing to internal frame buffer");
}
}

};