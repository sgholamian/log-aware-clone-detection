//,temp,sample_5511.java,2,17,temp,sample_5513.java,2,17
//,2
public class xxx {
public void dummy_method(){
org.apache.thrift.TBase msg;
CloseOperation_result result = new CloseOperation_result();
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