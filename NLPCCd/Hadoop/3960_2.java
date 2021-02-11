//,temp,sample_5768.java,2,11,temp,sample_7417.java,2,12
//,3
public class xxx {
public static void deleteQuietly(FileSystem fs, Path path, boolean recursive) throws IOException {
if (fs != null && path != null) {
try {
fs.delete(path, recursive);
} catch (IOException e) {


log.info("when deleting");
}
}
}

};