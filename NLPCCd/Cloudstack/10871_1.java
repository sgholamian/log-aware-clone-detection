//,temp,sample_9188.java,2,12,temp,sample_1992.java,2,12
//,3
public class xxx {
public FormatInfo process(String templatePath, ImageFormat format, String templateName) throws InternalErrorException {
if (format != null) {
return null;
}
String vhdPath = templatePath + File.separator + templateName + "." + ImageFormat.VHD.getFileExtension();
if (!_storage.exists(vhdPath)) {


log.info("unable to find the vhd file");
}
}

};