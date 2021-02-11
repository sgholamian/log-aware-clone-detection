//,temp,VhdProcessor.java,64,92,temp,TARProcessor.java,41,66
//,3
public class xxx {
    @Override
    public FormatInfo process(String templatePath, ImageFormat format, String templateName, long processTimeout) {
        if (format != null) {
            s_logger.debug("We currently don't handle conversion from " + format + " to TAR.");
            return null;
        }

        String tarPath = templatePath + File.separator + templateName + "." + ImageFormat.TAR.getFileExtension();

        if (!_storage.exists(tarPath)) {
            s_logger.debug("Unable to find the tar file: " + tarPath);
            return null;
        }

        FormatInfo info = new FormatInfo();
        info.format = ImageFormat.TAR;
        info.filename = templateName + "." + ImageFormat.TAR.getFileExtension();

        File tarFile = _storage.getFile(tarPath);

        info.size = _storage.getSize(tarPath);

        info.virtualSize = getVirtualSize(tarFile);

        return info;
    }

};