//,temp,sample_7406.java,2,14,temp,sample_5616.java,2,14
//,2
public class xxx {
public void validateConfiguration() {
for (ChannelHandler encoder : encoders) {
if (encoder instanceof ChannelHandlerFactory) {
continue;
}
if (ObjectHelper.getAnnotation(encoder, ChannelHandler.Sharable.class) != null) {
continue;
}


log.info("the encoder is not shareable or an channelhandlerfactory instance the encoder cannot safely be used");
}
}

};