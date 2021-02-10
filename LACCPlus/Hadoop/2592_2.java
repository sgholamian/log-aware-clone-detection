//,temp,TestMapProgress.java,151,154,temp,BackupNode.java,296,302
//,3
public class xxx {
    @Override
    public FenceResponse fence(JournalInfo journalInfo, long epoch,
        String fencerInfo) throws IOException {
      LOG.info("Fenced by " + fencerInfo + " with epoch " + epoch);
      throw new UnsupportedOperationException(
          "BackupNode does not support fence");
    }

};