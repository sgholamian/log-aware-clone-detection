//,temp,sample_7869.java,2,18,temp,sample_7874.java,2,18
//,2
public class xxx {
public void dummy_method(){
TemplateProp vInfo = loc.getTemplateInfo();
if ((vInfo.getSize() == vInfo.getPhysicalSize()) && (vInfo.getInstallPath().endsWith(ImageFormat.OVA.getFileExtension()))) {
try {
Processor processor = _processors.get("OVA Processor");
OVAProcessor vmdkProcessor = (OVAProcessor)processor;
long vSize = vmdkProcessor.getTemplateVirtualSize(path, vInfo.getInstallPath().substring(vInfo.getInstallPath().lastIndexOf(File.separator) + 1));
vInfo.setSize(vSize);
loc.updateVirtualSize(vSize);
loc.save();
} catch (Exception e) {


log.info("unable to get the virtual size of the volume due to");
}
}
}

};