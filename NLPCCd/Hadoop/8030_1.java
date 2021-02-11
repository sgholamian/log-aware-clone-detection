//,temp,sample_3668.java,2,12,temp,sample_3667.java,2,11
//,3
public class xxx {
protected void renameToFailure(Path src, Path dst) throws IOException {
try {
getStore().rename(src, dst);
fail("Expected failure renaming " + src + " to " + dst + "- but got success");
} catch (SwiftOperationFailedException e) {
} catch (FileNotFoundException e) {


log.info("rename failed expected");
}
}

};