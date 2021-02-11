//,temp,sample_3886.java,2,14,temp,sample_4589.java,2,11
//,3
public class xxx {
public static String getPidFromPidFile(String pidFileName) {
BufferedReader pidFile = null;
FileReader fReader = null;
String pid = null;
try {
fReader = new FileReader(pidFileName);
pidFile = new BufferedReader(fReader);
} catch (FileNotFoundException f) {


log.info("pidfile doesn t exist");
}
}

};