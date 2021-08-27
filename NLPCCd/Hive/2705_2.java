//,temp,sample_3671.java,2,14,temp,sample_3670.java,2,11
//,3
public class xxx {
public static String getDaemonLocalDirString(Configuration conf, String workDirsEnvString) {
String localDirList = HiveConf.getVar(conf, ConfVars.LLAP_DAEMON_WORK_DIRS);
if (localDirList != null && !localDirList.isEmpty()) {
if (!localDirList.equalsIgnoreCase("useYarnEnvDirs") && !StringUtils.isBlank(localDirList)) {


log.info("using local dirs from configuration");
}
}
}

};