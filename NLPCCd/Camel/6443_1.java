//,temp,sample_7357.java,2,17,temp,sample_551.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (is == null) {
String charset = endpoint.getCharset();
if (charset != null) {
is = new ByteArrayInputStream(exchange.getIn().getMandatoryBody(String.class).getBytes(charset));
} else {
is = exchange.getIn().getMandatoryBody(InputStream.class);
}
}
final StopWatch watch = new StopWatch();
if (endpoint.getFileExist() == GenericFileExist.Append) {


log.info("client appendfile");
}
}

};