//,temp,sample_5053.java,2,11,temp,sample_2790.java,2,11
//,2
public class xxx {
private Charset getEncodingParameter(String type, Mina2Configuration configuration) {
String encoding = configuration.getEncoding();
if (encoding == null) {
encoding = Charset.defaultCharset().name();
configuration.setEncoding(encoding);


log.info("no encoding parameter using default charset");
}
}

};