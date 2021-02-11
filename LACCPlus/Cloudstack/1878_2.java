//,temp,IsoProcessor.java,43,64,temp,RawImageProcessor.java,53,72
//,3
public class xxx {
    @Override
    public FormatInfo process(String templatePath, ImageFormat format, String templateName, long processTimeout) throws InternalErrorException {
        if (format != null) {
            s_logger.debug("We currently don't handle conversion from " + format + " to raw image.");
            return null;
        }

        String imgPath = templatePath + File.separator + templateName + "." + ImageFormat.RAW.getFileExtension();
        if (!_storage.exists(imgPath)) {
            s_logger.debug("Unable to find raw image:" + imgPath);
            return null;
        }
        FormatInfo info = new FormatInfo();
        info.format = ImageFormat.RAW;
        info.filename = templateName + "." + ImageFormat.RAW.getFileExtension();
        info.size = _storage.getSize(imgPath);
        info.virtualSize = info.size;
        s_logger.debug("Process raw image " + info.filename + " successfully");
        return info;
    }

};