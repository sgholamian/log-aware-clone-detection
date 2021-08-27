//,temp,sample_3714.java,2,13,temp,sample_3716.java,2,12
//,3
public class xxx {
private Object parseMessageToByteArray(IoBuffer buf) throws CharacterCodingException {
int len = buf.limit() - 3;
byte[] dst = new byte[len];
buf.skip(1);
buf.get(dst, 0, len);
buf.skip(2);
if (config.isConvertLFtoCR()) {


log.info("replacing lf by cr");
}
}

};