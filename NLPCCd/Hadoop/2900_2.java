//,temp,sample_3892.java,2,16,temp,sample_7258.java,2,16
//,2
public class xxx {
private static boolean isSetsidSupported() {
ShellCommandExecutor shexec = null;
boolean setsidSupported = true;
try {
String[] args = {"setsid", "bash", "-c", "echo $$"};
shexec = new ShellCommandExecutor(args);
shexec.execute();
} catch (IOException ioe) {
setsidSupported = false;
} finally {


log.info("setsid exited with exit code");
}
}

};