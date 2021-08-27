//,temp,sample_5048.java,2,17,temp,sample_2784.java,2,16
//,3
public class xxx {
public void dummy_method(){
Charset charset = getEncodingParameter(type, configuration);
LineDelimiter delimiter = getLineDelimiterParameter(configuration.getTextlineDelimiter());
Mina2TextLineCodecFactory codecFactory = new Mina2TextLineCodecFactory(charset, delimiter);
if (configuration.getEncoderMaxLineLength() > 0) {
codecFactory.setEncoderMaxLineLength(configuration.getEncoderMaxLineLength());
}
if (configuration.getDecoderMaxLineLength() > 0) {
codecFactory.setDecoderMaxLineLength(configuration.getDecoderMaxLineLength());
}
addCodecFactory(service, codecFactory);


log.info("using textlinecodecfactory using encoding line delimiter");
}

};