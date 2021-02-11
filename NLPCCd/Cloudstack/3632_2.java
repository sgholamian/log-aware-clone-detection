//,temp,sample_4097.java,2,17,temp,sample_5251.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (disks == null || disks.isEmpty()) {
return null;
}
for (final KVMPhysicalDisk disk : disks) {
if (disk.getName().endsWith("qcow2")) {
templateVol = disk;
break;
}
}
if (templateVol == null) {


log.info("failed to get template from pool");
}
}

};