//,temp,sample_4168.java,2,14,temp,sample_2967.java,2,13
//,3
public class xxx {
public boolean isFileDeletable(FileStatus fStat) {
long currentTime = EnvironmentEdgeManager.currentTime();
long time = fStat.getModificationTime();
long life = currentTime - time;
if (LOG.isTraceEnabled()) {
}
if (life < 0) {


log.info("found a hfile newer than current time probably a clock skew");
}
}

};