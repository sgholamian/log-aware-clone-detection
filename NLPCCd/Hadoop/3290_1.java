//,temp,sample_1154.java,2,13,temp,sample_1156.java,2,17
//,3
public class xxx {
public boolean deleteObject(Path path) throws IOException {
SwiftObjectPath swiftObjectPath = toObjectPath(path);
if (!SwiftUtils.isRootDir(swiftObjectPath)) {
return swiftRestClient.delete(swiftObjectPath);
} else {
if (LOG.isDebugEnabled()) {


log.info("not deleting root directory entry");
}
}
}

};