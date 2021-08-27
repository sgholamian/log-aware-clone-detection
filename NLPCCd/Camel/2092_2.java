//,temp,sample_7857.java,2,17,temp,sample_5049.java,2,17
//,2
public class xxx {
public void dummy_method(){
LineDelimiter delimiter = getLineDelimiterParameter(configuration.getTextlineDelimiter());
Mina2TextLineCodecFactory codecFactory = new Mina2TextLineCodecFactory(charset, delimiter);
if (configuration.getEncoderMaxLineLength() > 0) {
codecFactory.setEncoderMaxLineLength(configuration.getEncoderMaxLineLength());
}
if (configuration.getDecoderMaxLineLength() > 0) {
codecFactory.setDecoderMaxLineLength(configuration.getDecoderMaxLineLength());
}
addCodecFactory(service, codecFactory);
if (LOG.isDebugEnabled()) {


log.info("encoder maximum line length decoder maximum line length");
}
}

};