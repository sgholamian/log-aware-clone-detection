//,temp,sample_4233.java,2,13,temp,sample_137.java,2,17
//,3
public class xxx {
public void dummy_method(){
dfsClient.checkOpen();
if ((extendedReadBuffers != null) && (!extendedReadBuffers.isEmpty())) {
final StringBuilder builder = new StringBuilder();
extendedReadBuffers.visitAll(new IdentityHashStore.Visitor<ByteBuffer, Object>() {
private String prefix = "";
public void accept(ByteBuffer k, Object v) {
builder.append(prefix).append(k);
prefix = ", ";
}
});


log.info("closing file but there are still unreleased bytebuffers allocated by read please release");
}
}

};