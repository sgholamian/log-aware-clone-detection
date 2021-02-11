//,temp,sample_3892.java,2,16,temp,sample_4852.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (Shell.WINDOWS) {
return false;
}
ShellCommandExecutor shexec = null;
boolean setsidSupported = true;
try {
String[] args = {"setsid", "bash", "-c", "echo $$"};
shexec = new ShellCommandExecutor(args);
shexec.execute();
} catch (IOException ioe) {


log.info("setsid is not available on this machine so not using it");
}
}

};