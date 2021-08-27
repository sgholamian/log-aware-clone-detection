//,temp,sample_3245.java,2,15,temp,sample_3247.java,2,17
//,3
public class xxx {
public static InputStream genericFileToInputStream(GenericFile<?> file, Exchange exchange) throws IOException, NoTypeConversionAvailableException {
if (file.getFile() instanceof File) {
File f = (File) file.getFile();
if (f.exists()) {
String charset = file.getCharset();
if (charset != null) {
} else {


log.info("read file no charset");
}
}
}
}

};