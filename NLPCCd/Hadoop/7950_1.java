//,temp,sample_2913.java,2,18,temp,sample_6921.java,2,13
//,3
public class xxx {
public void dummy_method(){
boolean success = false;
Exception e = null;
try {
Path absolutePathToBeDeleted = new Path(volume, pathToBeDeleted);
success = localFileSystem.delete(absolutePathToBeDeleted, true);
} catch (Exception ex) {
e = ex;
}
if (!success) {
if (e != null) {


log.info("failure in with exception");
}
}
}

};