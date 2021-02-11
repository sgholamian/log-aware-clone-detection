//,temp,sample_4942.java,2,21,temp,sample_1381.java,2,18
//,3
public class xxx {
public void dummy_method(){
readSelector.select();
Iterator<SelectionKey> iter = readSelector.selectedKeys().iterator();
while (iter.hasNext()) {
key = iter.next();
iter.remove();
try {
if (key.isReadable()) {
doRead(key);
}
} catch (CancelledKeyException cke) {


log.info("connection aborted from");
}
}
}

};