//,temp,sample_4199.java,2,17,temp,sample_7587.java,2,17
//,2
public class xxx {
public void dummy_method(){
String etype = EventTypes.EVENT_TEMPLATE_CREATE;
if (tmplt.getFormat() == ImageFormat.ISO) {
etype = EventTypes.EVENT_ISO_CREATE;
}
long physicalSize = 0;
DataStore ds = template.getDataStore();
TemplateDataStoreVO tmpltStore = _vmTemplateStoreDao.findByStoreTemplate(ds.getId(), template.getId());
if (tmpltStore != null) {
physicalSize = tmpltStore.getPhysicalSize();
} else {


log.info("no entry found in template store ref for template id and image store id at the end of registering template");
}
}

};