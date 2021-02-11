//,temp,QCOW2Processor.java,48,78,temp,IsoProcessor.java,43,64
//,3
public class xxx {
   @Override
    public FormatInfo process(String templatePath, ImageFormat format, String templateName, long processTimeout) {
        if (format != null) {
            s_logger.debug("We don't handle conversion from " + format + " to ISO.");
            return null;
        }

        String isoPath = templatePath + File.separator + templateName + "." + ImageFormat.ISO.getFileExtension();

        if (!_storage.exists(isoPath)) {
            s_logger.debug("Unable to find the iso file: " + isoPath);
            return null;
        }

        FormatInfo info = new FormatInfo();
        info.format = ImageFormat.ISO;
        info.filename = templateName + "." + ImageFormat.ISO.getFileExtension();
        info.size = _storage.getSize(isoPath);
        info.virtualSize = info.size;

        return info;
    }

};