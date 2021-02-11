//,temp,sample_2811.java,2,9,temp,sample_2814.java,2,9
//,2
public class xxx {
public String createOvaForTemplate(TemplateObjectTO template) {
DataStoreTO storeTO = template.getDataStore();
if (!(storeTO instanceof NfsTO)) {


log.info("can only handle nfs storage while creating ova from template");
}
}

};