//,temp,QCOW2Processor.java,80,89,temp,OVAProcessor.java,118,128
//,3
public class xxx {
    @Override
    public long getVirtualSize(File file) {
        try {
            long size = getTemplateVirtualSize(file.getParent(), file.getName());
            return size;
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failed to get virtual template size for ova: " + e.getLocalizedMessage());
        }
        return file.length();
    }

};