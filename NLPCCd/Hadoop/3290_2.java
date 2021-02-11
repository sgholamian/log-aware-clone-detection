//,temp,sample_1154.java,2,13,temp,sample_1156.java,2,17
//,3
public class xxx {
public void dummy_method(){
SwiftObjectPath destObject = toObjectPath(dst);
if (SwiftUtils.isRootDir(srcObject)) {
throw new SwiftOperationFailedException("cannot rename root dir");
}
final SwiftFileStatus srcMetadata;
srcMetadata = getObjectMetadata(src);
SwiftFileStatus dstMetadata;
try {
dstMetadata = getObjectMetadata(dst);
} catch (FileNotFoundException e) {


log.info("destination does not exist");
}
}

};