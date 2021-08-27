//,temp,sample_532.java,2,16,temp,sample_537.java,2,16
//,3
public class xxx {
public void dummy_method(){
String remoteName = name;
String currentDir = null;
if (endpoint.getConfiguration().isStepwise()) {
currentDir = getCurrentDirectory();
String path = FileUtil.onlyPath(name);
if (path != null) {
changeCurrentDirectory(path);
}
remoteName = FileUtil.stripPath(name);
}


log.info("client retrievefile");
}

};