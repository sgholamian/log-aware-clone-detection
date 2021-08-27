//,temp,sample_923.java,2,18,temp,sample_922.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (doTrace) {
trace.logCompositeOrcCb(lastChunkLength, lastChunk.getChunk().remaining(), cc);
}
lastChunk.getChunk().position(lastChunk.getChunk().position() + lastChunkLength);
if (lastChunk.getChunk().remaining() <= 0) {
if (isTracingEnabled) {
}
lastChunk.replaceSelfWith(cc);
} else {
if (isTracingEnabled) {


log.info("adding before in the buffers");
}
}
}

};