//,temp,sample_4168.java,2,14,temp,sample_2967.java,2,13
//,3
public class xxx {
public boolean isFileDeletable(FileStatus fStat) {
if (!MasterProcedureUtil.validateProcedureWALFilename(fStat.getPath().getName())) {
return true;
}
long currentTime = EnvironmentEdgeManager.currentTime();
long time = fStat.getModificationTime();
long life = currentTime - time;
if (LOG.isTraceEnabled()) {


log.info("procedure log life ttl current from");
}
}

};