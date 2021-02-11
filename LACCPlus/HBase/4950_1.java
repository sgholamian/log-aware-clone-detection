//,temp,TableBackupClient.java,351,363,temp,TableBackupClient.java,163,180
//,3
public class xxx {
  protected void cleanupDistCpLog(BackupInfo backupInfo, Configuration conf) throws IOException {
    Path rootPath = new Path(backupInfo.getHLogTargetDir()).getParent();
    FileStatus[] files = FSUtils.listStatus(fs, rootPath);
    if (files == null) {
      return;
    }
    for (FileStatus file : files) {
      if (file.getPath().getName().startsWith("_distcp_logs")) {
        LOG.debug("Delete log files of DistCp: " + file.getPath().getName());
        FSUtils.delete(fs, file.getPath(), true);
      }
    }
  }

};