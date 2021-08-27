//,temp,sample_3709.java,2,19,temp,sample_3710.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (current == config.getEndByte2() && state.previous() == config.getEndByte1()) {
if (state.isStarted()) {
int currentPosition = in.position();
int currentLimit = in.limit();
in.position(state.start());
in.limit(currentPosition);
try {
out.write(config.isProduceString() ? parseMessageToString(in.slice(), charsetDecoder(session)) : parseMessageToByteArray(in.slice()));
messageDecoded = true;
} finally {


log.info("resetting to position and limit to");
}
}
}
}

};