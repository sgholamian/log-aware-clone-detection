//,temp,sample_1578.java,2,12,temp,sample_1577.java,2,10
//,3
public class xxx {
protected boolean doSafePollSubDirectory(String absolutePath, String dirName, List<GenericFile<T>> fileList, int depth) {
try {
return doPollDirectory(absolutePath, dirName, fileList, depth);
} catch (Exception e) {
if (ignoreCannotRetrieveFile(absolutePath, null, e)) {


log.info("ignoring file error for");
}
}
}

};