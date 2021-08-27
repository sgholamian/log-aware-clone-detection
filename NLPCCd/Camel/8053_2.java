//,temp,sample_6668.java,2,16,temp,sample_7076.java,2,16
//,3
public class xxx {
public void dummy_method(){
request.headers().set(HttpHeaders.Names.HOST, hostHeader);
String connection = message.getHeader(HttpHeaders.Names.CONNECTION, String.class);
if (connection == null) {
if (configuration.isKeepAlive()) {
connection = HttpHeaders.Values.KEEP_ALIVE;
} else {
connection = HttpHeaders.Values.CLOSE;
}
}
request.headers().set(HttpHeaders.Names.CONNECTION, connection);


log.info("connection");
}

};