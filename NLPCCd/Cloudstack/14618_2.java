//,temp,sample_1992.java,2,12,temp,sample_2777.java,2,12
//,2
public class xxx {
public FormatInfo process(String templatePath, ImageFormat format, String templateName) {
if (format != null) {
return null;
}
String isoPath = templatePath + File.separator + templateName + "." + ImageFormat.ISO.getFileExtension();
if (!_storage.exists(isoPath)) {


log.info("unable to find the iso file");
}
}

};