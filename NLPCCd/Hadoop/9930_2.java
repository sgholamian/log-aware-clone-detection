//,temp,sample_1248.java,2,14,temp,sample_8709.java,2,14
//,3
public class xxx {
public void setup() {
securityManager = System.getSecurityManager();
System.setSecurityManager(new NoExitSecurityManager());
try {
fs = FileSystem.get(getConf());
root = new Path("target/tmp").makeQualified(fs.getUri(), fs.getWorkingDirectory()).toString();
TestDistCpUtils.delete(fs, root);
} catch (IOException e) {


log.info("exception encountered");
}
}

};