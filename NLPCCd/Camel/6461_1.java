//,temp,sample_7856.java,2,17,temp,sample_2785.java,2,16
//,3
public class xxx {
public void dummy_method(){
LineDelimiter delimiter = getLineDelimiterParameter(configuration.getTextlineDelimiter());
TextLineCodecFactory codecFactory = new TextLineCodecFactory(charset, delimiter);
if (configuration.getEncoderMaxLineLength() > 0) {
codecFactory.setEncoderMaxLineLength(configuration.getEncoderMaxLineLength());
}
if (configuration.getDecoderMaxLineLength() > 0) {
codecFactory.setDecoderMaxLineLength(configuration.getDecoderMaxLineLength());
}
addCodecFactory(config, codecFactory);
if (LOG.isDebugEnabled()) {


log.info("using textlinecodecfactory using encoding line delimiter");
}
}

};