//,temp,QCOW2Processor.java,80,89,temp,VhdProcessor.java,94,103
//,2
public class xxx {
    @Override
    public long getVirtualSize(File file) throws IOException {
        try {
            long size = getTemplateVirtualSize(file);
            return size;
        } catch (Exception e) {
            s_logger.info("[ignored]" + "failed to get template virtual size for VHD: " + e.getLocalizedMessage());
        }
        return file.length();
    }

};