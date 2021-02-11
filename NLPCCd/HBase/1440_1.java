//,temp,sample_4057.java,2,8,temp,sample_1929.java,2,7
//,3
public class xxx {
public CompactionRequestImpl selectCompaction(Collection<HStoreFile> candidateFiles, List<HStoreFile> filesCompacting, boolean isUserCompaction, boolean mayUseOffPeak, boolean forceMajor) throws IOException {
if(forceMajor){


log.info("major compaction is not supported for fifo compaction policy ignore the flag");
}
}

};