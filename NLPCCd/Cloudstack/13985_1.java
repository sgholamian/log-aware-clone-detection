//,temp,sample_6150.java,2,12,temp,sample_1992.java,2,12
//,3
public class xxx {
public FormatInfo process(String templatePath, ImageFormat format, String templateName) throws InternalErrorException {
if (format != null) {
return null;
}
String imgPath = templatePath + File.separator + templateName + "." + ImageFormat.RAW.getFileExtension();
if (!_storage.exists(imgPath)) {


log.info("unable to find raw image");
}
}

};