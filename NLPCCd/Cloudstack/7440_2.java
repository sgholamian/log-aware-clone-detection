//,temp,sample_223.java,2,11,temp,sample_9190.java,2,11
//,2
public class xxx {
public long getVirtualSize(File file) throws IOException {
try {
long size = getTemplateVirtualSize(file);
return size;
} catch (Exception e) {


log.info("ignored failed to get template virtual size for vhd");
}
}

};