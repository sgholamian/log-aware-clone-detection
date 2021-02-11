//,temp,sample_7354.java,2,11,temp,sample_309.java,2,11
//,2
public class xxx {
public long getVirtualSize(File file) {
try {
long size = getTemplateVirtualSize(file.getParent(), file.getName());
return size;
} catch (Exception e) {


log.info("ignored failed to get virtual template size for ova");
}
}

};