//,temp,sample_7258.java,2,16,temp,sample_4849.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (Shell.WINDOWS) {
return false;
}
ShellCommandExecutor shexec;
boolean supported = true;
try {
String[] args = {"bash", "-c", "echo 1000"};
shexec = new ShellCommandExecutor(args);
shexec.execute();
} catch (InterruptedIOException iioe) {


log.info("interrupted unable to determine if bash is supported");
}
}

};