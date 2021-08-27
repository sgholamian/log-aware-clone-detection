//,temp,sample_1655.java,2,17,temp,sample_1649.java,2,17
//,2
public class xxx {
public void dummy_method(){
org.apache.thrift.TBase msg;
remove_master_key_result result = new remove_master_key_result();
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