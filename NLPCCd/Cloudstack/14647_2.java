//,temp,sample_8404.java,2,17,temp,sample_8407.java,2,17
//,2
public class xxx {
public Answer dettachIso(final DettachCommand cmd) {
final DiskTO disk = cmd.getDisk();
final DataTO data = disk.getData();
final DataStoreTO store = data.getDataStore();
String isoURL = null;
if (store == null) {
final TemplateObjectTO iso = (TemplateObjectTO) disk.getData();
isoURL = iso.getName();
} else {
if (!(store instanceof NfsTO)) {


log.info("can t attach a iso which is not created on nfs");
}
}
}

};