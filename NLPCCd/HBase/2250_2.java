//,temp,sample_2410.java,2,11,temp,sample_1064.java,2,10
//,3
public class xxx {
protected void makeAssertions(ImmutableBytesWritable key, Iterable<ImmutableBytesWritable> values) {
int count = 0;
for (ImmutableBytesWritable value : values) {
String val = Bytes.toStringBinary(value.get());


log.info("reduce key value");
}
}

};