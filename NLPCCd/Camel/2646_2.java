//,temp,sample_7016.java,2,17,temp,sample_7010.java,2,17
//,2
public class xxx {
public void dummy_method(){
byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
org.apache.thrift.TSerializable msg;
echo_result result = new echo_result();
if (e instanceof org.apache.thrift.transport.TTransportException) {
fb.close();
return;
} else if (e instanceof org.apache.thrift.TApplicationException) {
msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
msg = (org.apache.thrift.TApplicationException)e;
} else {


log.info("exception inside handler");
}
}

};