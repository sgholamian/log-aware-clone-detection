//,temp,sample_1615.java,2,13,temp,sample_1618.java,2,13
//,2
public class xxx {
public void testUMALauncherError() throws Exception {
String classpath = getTestRuntimeClasspath();
String javaHome = System.getenv("JAVA_HOME");
if (javaHome == null) {
return;
}
String[] args = {
"--classpath", classpath, "--queue", "default", "--cmd", javaHome + "/bin/java -Xmx512m " + TestUnmanagedAMLauncher.class.getCanonicalName() + " failure" };


log.info("initializing launcher");
}

};