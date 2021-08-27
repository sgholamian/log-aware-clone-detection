//,temp,sample_3598.java,2,19,temp,sample_5031.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (isTagged) {
int size = keyWritable.getSize() - 1;
tag = keyWritable.get()[size];
keyWritable.setSize(size);
}
if (!keyWritable.equals(groupKey)) {
if (groupKey == null) {
groupKey = new BytesWritable();
} else {
if (LOG.isTraceEnabled()) {


log.info("end group");
}
}
}
}

};