//,temp,sample_8344.java,2,16,temp,sample_6829.java,2,14
//,3
public class xxx {
private void shutdownCleanup() {
for (String mountPoint : _storageMounts.values()) {
String result = null;
Script command = new Script(true, "umount", _timeout, s_logger);
command.add(mountPoint);
result = command.execute();
if (result != null) {


log.info("unable to umount due to");
}
}
}

};