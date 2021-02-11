//,temp,QCOW2Processor.java,48,78,temp,RawImageProcessor.java,53,72
//,3
public class xxx {
    @Override
    public FormatInfo process(String templatePath, ImageFormat format, String templateName, long processTimeout) throws InternalErrorException {
        if (format != null) {
            s_logger.debug("We currently don't handle conversion from " + format + " to QCOW2.");
            return null;
        }

        String qcow2Path = templatePath + File.separator + templateName + "." + ImageFormat.QCOW2.getFileExtension();

        if (!_storage.exists(qcow2Path)) {
            s_logger.debug("Unable to find the qcow2 file: " + qcow2Path);
            return null;
        }

        FormatInfo info = new FormatInfo();
        info.format = ImageFormat.QCOW2;
        info.filename = templateName + "." + ImageFormat.QCOW2.getFileExtension();

        File qcow2File = _storage.getFile(qcow2Path);

        info.size = _storage.getSize(qcow2Path);

        try {
            info.virtualSize = getTemplateVirtualSize(qcow2File);
        } catch (IOException e) {
            s_logger.error("Unable to get virtual size from " + qcow2File.getName());
            throw new InternalErrorException("unable to get virtual size from qcow2 file");
        }

        return info;
    }

};