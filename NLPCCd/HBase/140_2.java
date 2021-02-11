//,temp,sample_1606.java,2,7,temp,sample_1924.java,2,10
//,3
public class xxx {
private static TProtocolFactory getTProtocolFactory(boolean isCompact) {
if (isCompact) {
return new TCompactProtocol.Factory();
} else {


log.info("using binary protocol");
}
}

};