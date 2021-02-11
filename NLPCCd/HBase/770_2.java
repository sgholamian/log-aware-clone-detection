//,temp,sample_5465.java,2,19,temp,sample_5510.java,2,19
//,3
public class xxx {
public void dummy_method(){
reader =  createWALReaderForPrimary();
long flushSeqId = -1;
while (true) {
WAL.Entry entry = reader.next();
if (entry == null) {
break;
}
FlushDescriptor flushDesc = WALEdit.getFlushDescriptor(entry.getEdit().getCells().get(0));
if (flushDesc != null) {
if (flushDesc.getAction() == FlushAction.START_FLUSH) {


log.info("replaying flush start in secondary");
}
}
}
}

};