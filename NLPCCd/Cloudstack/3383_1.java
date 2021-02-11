//,temp,sample_4286.java,2,19,temp,sample_4285.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (tmpltFiles == null || tmpltFiles.length == 0) {
details = "No files under volume parent directory " + tmpltParent.getName();
s_logger.debug(details);
} else {
boolean found = false;
for (File f : tmpltFiles) {
if (!found && f.getName().equals("volume.properties")) {
found = true;
}
if (f.isDirectory() && f.getName().equals("KVMHA")) {


log.info("deleting kvmha directory contents from template location");
}
}
}
}

};