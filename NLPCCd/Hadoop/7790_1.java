//,temp,sample_5581.java,2,17,temp,sample_2203.java,2,17
//,3
public class xxx {
public void dummy_method(){
Path[] restChunkFiles = new Path[allChunkPaths.size()];
allChunkPaths.toArray(restChunkFiles);
if (LOG.isDebugEnabled()) {
int i = 0;
for (Path f : restChunkFiles) {
++i;
}
}
dstfs.concat(firstChunkFile, restChunkFiles);
if (LOG.isDebugEnabled()) {


log.info("concat result");
}
}

};