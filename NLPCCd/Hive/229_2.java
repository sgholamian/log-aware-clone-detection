//,temp,sample_1681.java,2,17,temp,sample_1711.java,2,17
//,2
public class xxx {
public void dummy_method(){
org.apache.thrift.TBase msg;
put_file_metadata_result result = new put_file_metadata_result();
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