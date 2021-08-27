//,temp,MinaConsumer.java,245,268,temp,MinaProducer.java,345,366
//,3
public class xxx {
    protected void configureDefaultCodecFactory(String type, IoService service) {
        if (configuration.isTextline()) {
            Charset charset = getEncodingParameter(type, configuration);
            LineDelimiter delimiter = getLineDelimiterParameter(configuration.getTextlineDelimiter());
            MinaTextLineCodecFactory codecFactory = new MinaTextLineCodecFactory(charset, delimiter);
            if (configuration.getEncoderMaxLineLength() > 0) {
                codecFactory.setEncoderMaxLineLength(configuration.getEncoderMaxLineLength());
            }
            if (configuration.getDecoderMaxLineLength() > 0) {
                codecFactory.setDecoderMaxLineLength(configuration.getDecoderMaxLineLength());
            }
            addCodecFactory(service, codecFactory);
            LOG.debug("{}: Using TextLineCodecFactory: {} using encoding: {} line delimiter: {}({})",
                    type, codecFactory, charset, configuration.getTextlineDelimiter(), delimiter);
            LOG.debug("Encoder maximum line length: {}. Decoder maximum line length: {}",
                    codecFactory.getEncoderMaxLineLength(), codecFactory.getDecoderMaxLineLength());
        } else {
            ObjectSerializationCodecFactory codecFactory = new ObjectSerializationCodecFactory();
            addCodecFactory(service, codecFactory);
            LOG.debug("{}: Using ObjectSerializationCodecFactory: {}", type, codecFactory);
        }
    }

};