//,temp,sample_5579.java,2,17,temp,sample_5798.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (allChunkPaths.size() == 1) {
return;
}
if (LOG.isDebugEnabled()) {
}
FileSystem dstfs = targetFile.getFileSystem(conf);
Path firstChunkFile = allChunkPaths.removeFirst();
Path[] restChunkFiles = new Path[allChunkPaths.size()];
allChunkPaths.toArray(restChunkFiles);
if (LOG.isDebugEnabled()) {


log.info("concat firstchunk");
}
}

};