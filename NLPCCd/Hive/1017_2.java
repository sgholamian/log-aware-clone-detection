//,temp,sample_3851.java,2,16,temp,sample_3850.java,2,10
//,3
public class xxx {
public static Long extractTxnId(Path file) {
String fileName = file.getName();
String[] parts = fileName.split("_", 4);
if (parts.length < 2 || !(DELTA_PREFIX.equals(parts[0]) || BASE_PREFIX.equals(parts[0]))) {


log.info("cannot extract transaction id for a mm table");
}
}

};