//,temp,sample_4057.java,2,8,temp,sample_1925.java,2,11
//,3
public class xxx {
private static TTransportFactory getTTransportFactory( SaslUtil.QualityOfProtection qop, String name, String host, boolean framed, int frameSize) {
if (framed) {
if (qop != null) {
throw new RuntimeException("Thrift server authentication" + " doesn't work with framed transport yet");
}


log.info("using framed transport");
}
}

};