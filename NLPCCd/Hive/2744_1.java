//,temp,sample_4447.java,2,15,temp,sample_4446.java,2,13
//,3
public class xxx {
private void  mvFileToFinalPath(Path specPath, Configuration hconf, boolean success, Logger log) throws IOException, HiveException {
FileSystem fs = specPath.getFileSystem(hconf);
Path tmpPath = Utilities.toTempPath(specPath);
Path intermediatePath = new Path(tmpPath.getParent(), tmpPath.getName() + ".intermediate");
if (success) {
if (fs.exists(tmpPath)) {
Utilities.rename(fs, tmpPath, intermediatePath);
Utilities.removeTempOrDuplicateFiles(fs, intermediatePath, false);


log.info("moving tmp dir to");
}
}
}

};