//,temp,sample_6668.java,2,16,temp,sample_7076.java,2,16
//,3
public class xxx {
public void dummy_method(){
request.headers().set(HttpHeaderNames.HOST.toString(), hostHeader);
String connection = message.getHeader(HttpHeaderNames.CONNECTION.toString(), String.class);
if (connection == null) {
if (configuration.isKeepAlive()) {
connection = HttpHeaderValues.KEEP_ALIVE.toString();
} else {
connection = HttpHeaderValues.CLOSE.toString();
}
}
request.headers().set(HttpHeaderNames.CONNECTION.toString(), connection);


log.info("connection");
}

};