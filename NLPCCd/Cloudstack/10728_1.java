//,temp,sample_221.java,2,12,temp,sample_1992.java,2,12
//,3
public class xxx {
public FormatInfo process(String templatePath, ImageFormat format, String templateName) throws InternalErrorException {
if (format != null) {
return null;
}
String qcow2Path = templatePath + File.separator + templateName + "." + ImageFormat.QCOW2.getFileExtension();
if (!_storage.exists(qcow2Path)) {


log.info("unable to find the file");
}
}

};