//,temp,sample_7351.java,2,16,temp,sample_308.java,2,16
//,2
public class xxx {
public FormatInfo process(String templatePath, ImageFormat format, String templateName) throws InternalErrorException {
if (format != null) {
if (s_logger.isInfoEnabled()) {
}
return null;
}
String templateFilePath = templatePath + File.separator + templateName + "." + ImageFormat.VMDK.getFileExtension();
if (!_storage.exists(templateFilePath)) {
if (s_logger.isInfoEnabled()) {


log.info("unable to find the vmware template file");
}
}
}

};