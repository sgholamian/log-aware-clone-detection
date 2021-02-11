//,temp,sample_3323.java,2,16,temp,sample_4169.java,2,16
//,2
public class xxx {
public boolean isFileDeletable(FileStatus fStat) {
if (!MasterProcedureUtil.validateProcedureWALFilename(fStat.getPath().getName())) {
return true;
}
long currentTime = EnvironmentEdgeManager.currentTime();
long time = fStat.getModificationTime();
long life = currentTime - time;
if (LOG.isTraceEnabled()) {
}
if (life < 0) {


log.info("found a procedure log newer than current time probably a clock skew");
}
}

};