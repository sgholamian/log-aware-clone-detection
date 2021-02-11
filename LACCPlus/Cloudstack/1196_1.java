//,temp,VhdProcessor.java,64,92,temp,RawImageProcessor.java,53,72
//,3
public class xxx {
    @Override
    public FormatInfo process(String templatePath, ImageFormat format, String templateName, long processTimeout) throws InternalErrorException {
        if (format != null) {
            s_logger.debug("We currently don't handle conversion from " + format + " to VHD.");
            return null;
        }

        String vhdPath = templatePath + File.separator + templateName + "." + ImageFormat.VHD.getFileExtension();
        if (!_storage.exists(vhdPath)) {
            s_logger.debug("Unable to find the vhd file: " + vhdPath);
            return null;
        }

        File vhdFile = _storage.getFile(vhdPath);

        FormatInfo info = new FormatInfo();
        info.format = ImageFormat.VHD;
        info.filename = templateName + "." + ImageFormat.VHD.getFileExtension();
        info.size = _storage.getSize(vhdPath);

        try {
            info.virtualSize = getTemplateVirtualSize(vhdFile);
        } catch (IOException e) {
            s_logger.error("Unable to get the virtual size for " + vhdPath);
            throw new InternalErrorException("unable to get virtual size from vhd file");
        }

        return info;
    }

};