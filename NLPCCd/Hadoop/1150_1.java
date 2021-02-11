//,temp,sample_7703.java,2,11,temp,sample_5768.java,2,11
//,3
public class xxx {
private boolean delBlockFromDisk(File blockFile, File metaFile, Block b) {
if (blockFile == null) {
return true;
}
if (!blockFile.delete()) {


log.info("not able to delete the block file");
}
}

};