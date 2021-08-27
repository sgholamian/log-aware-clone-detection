//,temp,sample_5482.java,2,17,temp,sample_5523.java,2,17
//,2
public class xxx {
public void dummy_method(){
org.apache.thrift.TBase msg;
GetQueryId_result result = new GetQueryId_result();
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