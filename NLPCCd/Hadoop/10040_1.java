//,temp,sample_1033.java,2,12,temp,sample_8821.java,2,9
//,3
public class xxx {
private COMMIT_STATUS handleSpecialWait(boolean fromRead, long commitOffset, Channel channel, int xid, Nfs3FileAttributes preOpAttr) {
if (!fromRead) {
CommitCtx commitCtx = new CommitCtx(commitOffset, channel, xid, preOpAttr);
pendingCommits.put(commitOffset, commitCtx);
}
if (LOG.isDebugEnabled()) {


log.info("return commit special wait");
}
}

};