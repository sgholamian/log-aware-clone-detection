//,temp,sample_1964.java,2,11,temp,sample_1965.java,2,13
//,3
public class xxx {
public static Task<?> getLoadCopyTask(ReplicationSpec replicationSpec, Path srcPath, Path dstPath, HiveConf conf) {
Task<?> copyTask = null;
LOG.debug("ReplCopyTask:getLoadCopyTask: {}=>{}", srcPath, dstPath);
if ((replicationSpec != null) && replicationSpec.isInReplicationScope()){
ReplCopyWork rcwork = new ReplCopyWork(srcPath, dstPath, false);


log.info("replcopytask trcwork");
}
}

};