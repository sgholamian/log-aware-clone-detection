//,temp,QCOW2Processor.java,80,89,temp,VmdkProcessor.java,77,87
//,3
public class xxx {
    @Override
    public long getVirtualSize(File file) {
        try {
            long size = getTemplateVirtualSize(file.getParent(), file.getName());
            return size;
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failed to get template virtual size for vmdk: " + e.getLocalizedMessage());
        }
        return file.length();
    }

};