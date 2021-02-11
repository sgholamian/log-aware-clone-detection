//,temp,sample_9195.java,2,19,temp,sample_9194.java,2,17
//,3
public class xxx {
private static void purgeMatching(File dir, List<Pattern> patterns, long minTxIdToKeep) throws IOException {
for (File f : FileUtil.listFiles(dir)) {
if (!f.isFile()) continue;
for (Pattern p : patterns) {
Matcher matcher = p.matcher(f.getName());
if (matcher.matches()) {
long txid = Long.parseLong(matcher.group(1));
if (txid < minTxIdToKeep) {
if (!f.delete()) {


log.info("unable to delete no longer needed data");
}
}
}
}
}
}

};