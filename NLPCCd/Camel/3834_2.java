//,temp,sample_3714.java,2,13,temp,sample_3716.java,2,12
//,3
public class xxx {
private Object parseMessageToString(IoBuffer buf, CharsetDecoder decoder) throws CharacterCodingException {
int len = buf.limit() - 3;
buf.skip(1);
String message = buf.getString(len, decoder);
buf.skip(2);
if (config.isConvertLFtoCR()) {


log.info("replacing lf by cr");
}
}

};