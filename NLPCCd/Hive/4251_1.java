//,temp,sample_4089.java,2,11,temp,sample_4090.java,2,12
//,3
public class xxx {
private static void tryDeleteAllMmFiles(FileSystem fs, Path specPath, Path manifestDir, int dpLevels, int lbLevels, JavaUtils.IdPathFilter filter, long txnId, int stmtId, Configuration conf, boolean isBaseDir) throws IOException {
Path[] files = getMmDirectoryCandidates( fs, specPath, dpLevels, lbLevels, filter, txnId, stmtId, conf, isBaseDir);
if (files != null) {
for (Path path : files) {


log.info("deleting on failure");
}
}
}

};