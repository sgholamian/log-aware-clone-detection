//,temp,sample_3553.java,2,14,temp,sample_1556.java,2,15
//,3
public class xxx {
public void testGrowIssue() throws Exception {
StringBuilder sb = new StringBuilder(size);
for (int i = 0; i < 1024; i++) {
sb.append("X");
}
final Buffer key = codec.marshallKey("foo");
for (int i = 0; i < size; i++) {
final Buffer data = codec.marshallKey(i + "-" + sb.toString());


log.info("updating");
}
}

};