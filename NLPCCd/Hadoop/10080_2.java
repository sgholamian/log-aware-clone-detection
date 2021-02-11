//,temp,sample_2438.java,2,16,temp,sample_2433.java,2,16
//,3
public class xxx {
public void dummy_method(){
final Long fileLength = dfs.getFileStatus(file).getLen();
final Long file2Length = dfs.getFileStatus(file2).getLen();
int ret = -1;
try {
ret = shell.run(new String[] {"-du", dir.toString()});
} catch (Exception e) {
System.err.println("Exception raised from DFSShell.run " + e.getLocalizedMessage());
}
assertEquals(0, ret);
String returnString = out.toString();


log.info("du return is");
}

};