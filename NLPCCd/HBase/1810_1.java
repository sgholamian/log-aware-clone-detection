//,temp,sample_3058.java,2,16,temp,sample_3059.java,2,17
//,3
public class xxx {
public void dummy_method(){
int res = 0;
String[] args = new String[4];
args[0] = "-snapshot";
args[1] = backupInfo.getSnapshotName(table);
args[2] = "-copy-to";
args[3] = backupInfo.getTableBackupDir(table);
String jobname = "Full-Backup_" + backupInfo.getBackupId() + "_" + table.getNameAsString();
if (LOG.isDebugEnabled()) {
}
conf.set(JOB_NAME_CONF_KEY, jobname);


log.info("copy snapshot to");
}

};