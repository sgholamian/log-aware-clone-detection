//,temp,QCOW2Processor.java,80,89,temp,OVAProcessor.java,118,128
//,3
public class xxx {
    @Override
    public long getVirtualSize(File file) throws IOException {
        try {
            long size = getTemplateVirtualSize(file);
            return size;
        } catch (Exception e) {
            s_logger.info("[ignored]" + "failed to get template virtual size for QCOW2: " + e.getLocalizedMessage());
        }
        return file.length();
    }

};