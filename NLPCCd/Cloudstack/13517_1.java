//,temp,sample_4551.java,2,17,temp,sample_4553.java,2,17
//,3
public class xxx {
public void dummy_method(){
Script iScsiAdmCmd = new Script(true, "iscsiadm", 0, s_logger);
iScsiAdmCmd.add("-m", "node");
iScsiAdmCmd.add("-T", getIqn(volumeUuid));
iScsiAdmCmd.add("-p", pool.getSourceHost() + ":" + pool.getSourcePort());
iScsiAdmCmd.add("-o", "new");
String result = iScsiAdmCmd.execute();
if (result != null) {
System.out.println("Failed to add iSCSI target " + volumeUuid);
return false;
} else {


log.info("successfully added iscsi target");
}
}

};