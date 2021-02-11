//,temp,OVAProcessor.java,118,128,temp,VhdProcessor.java,94,103
//,3
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