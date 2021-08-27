//,temp,sample_3063.java,2,13,temp,sample_3065.java,2,13
//,2
public class xxx {
public static boolean copy(FileSystem srcFS, Path src, FileSystem dstFS, Path dst, boolean deleteSource, boolean overwrite, Configuration conf) throws IOException {
boolean copied = false;
boolean triedDistcp = false;
if (srcFS.getUri().getScheme().equals("hdfs")) {
ContentSummary srcContentSummary = srcFS.getContentSummary(src);
if (srcContentSummary.getFileCount() > MetastoreConf.getLongVar(conf, ConfVars.REPL_COPYFILE_MAXNUMFILES) && srcContentSummary.getLength() > MetastoreConf.getLongVar(conf,ConfVars.REPL_COPYFILE_MAXSIZE)) {


log.info("launch distributed copy distcp job");
}
}
}

};