//,temp,sample_3244.java,2,14,temp,sample_3246.java,2,15
//,3
public class xxx {
private static BufferedReader genericFileToReader(GenericFile<?> file, Exchange exchange) throws IOException, NoTypeConversionAvailableException {
if (file.getFile() instanceof File) {
File f = (File) file.getFile();
if (!f.exists()) {
return null;
}
String charset = file.getCharset();
if (charset != null) {


log.info("read file with charset");
}
}
}

};