//,temp,sample_1391.java,2,19,temp,sample_3996.java,2,19
//,3
public class xxx {
public void dummy_method(){
while (iter.hasNext()) {
SelectionKey key = iter.next();
iter.remove();
try {
if (key.isWritable()) {
doAsyncWrite(key);
}
} catch (CancelledKeyException cke) {
RpcCall call = (RpcCall)key.attachment();
if (call != null) {


log.info("connection aborted from");
}
}
}
}

};