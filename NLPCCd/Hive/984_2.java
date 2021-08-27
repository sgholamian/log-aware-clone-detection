//,temp,sample_1659.java,2,17,temp,sample_5484.java,2,17
//,3
public class xxx {
public void dummy_method(){
org.apache.thrift.TBase msg;
CloseSession_result result = new CloseSession_result();
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