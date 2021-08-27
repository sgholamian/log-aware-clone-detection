//,temp,sample_923.java,2,18,temp,sample_922.java,2,18
//,3
public class xxx {
public void dummy_method(){
ProcCacheChunk cc = new ProcCacheChunk(cbStartOffset, cbEndOffset, !isUncompressed, fullCompressionBlock, futureAlloc, cacheBuffers.size() - 1);
toDecompress.add(cc);
if (isTracingEnabled) {
}
if (doTrace) {
trace.logCompositeOrcCb(lastChunkLength, lastChunk.getChunk().remaining(), cc);
}
lastChunk.getChunk().position(lastChunk.getChunk().position() + lastChunkLength);
if (lastChunk.getChunk().remaining() <= 0) {
if (isTracingEnabled) {


log.info("replacing with in the buffers");
}
}
}

};