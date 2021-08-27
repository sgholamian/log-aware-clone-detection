//,temp,sample_3709.java,2,19,temp,sample_3710.java,2,19
//,3
public class xxx {
public void dummy_method(){
boolean messageDecoded = false;
while (in.hasRemaining()) {
int previousPosition = in.position();
byte current = in.get();
if (current == config.getEndByte2() && state.previous() == config.getEndByte1()) {
if (state.isStarted()) {
int currentPosition = in.position();
int currentLimit = in.limit();
in.position(state.start());
in.limit(currentPosition);


log.info("set start to position and limit to");
}
}
}
}

};