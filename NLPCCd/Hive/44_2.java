//,temp,sample_1657.java,2,17,temp,sample_1693.java,2,17
//,2
public class xxx {
public void dummy_method(){
org.apache.thrift.TBase msg;
get_last_completed_transaction_for_table_result result = new get_last_completed_transaction_for_table_result();
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