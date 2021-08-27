//,temp,sample_3553.java,2,14,temp,sample_1556.java,2,15
//,3
public class xxx {
public void testGrowIssue() throws Exception {
StringBuilder sb = new StringBuilder(SIZE);
for (int i = 0; i < SIZE; i++) {
sb.append("X");
}
Exchange item = new DefaultExchange(context);
item.getIn().setBody(sb.toString(), String.class);
final String key = "foo";
for (int i = 0; i < SIZE; i++) {


log.info("updating");
}
}

};