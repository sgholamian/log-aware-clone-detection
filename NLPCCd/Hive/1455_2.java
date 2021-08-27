//,temp,sample_4131.java,2,12,temp,sample_4132.java,2,14
//,3
public class xxx {
private DataInput getNextStream() {
try {
if (resDir != null && resDirFilesNum < resDirPaths.length && (resDirPaths[resDirFilesNum] != null)) {
return resFs.open(resDirPaths[resDirFilesNum++]);
}
} catch (FileNotFoundException e) {
return null;
} catch (IOException e) {


log.info("getnextstream error");
}
}

};