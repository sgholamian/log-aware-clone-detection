//,temp,sample_2749.java,2,11,temp,sample_2733.java,2,8
//,3
public class xxx {
public static boolean isLocalFile(HiveConf conf, URI fileUri) {
try {
FileSystem fsForFile = FileSystem.get(fileUri, conf);
return LocalFileSystem.class.isInstance(fsForFile);
} catch (IOException e) {


log.info("unable to get filesystem for");
}
}

};