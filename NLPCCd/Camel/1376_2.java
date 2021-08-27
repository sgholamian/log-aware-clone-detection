//,temp,sample_3245.java,2,15,temp,sample_3247.java,2,17
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
return IOConverter.toReader(f, charset);
} else {


log.info("read file no charset");
}
}
}

};