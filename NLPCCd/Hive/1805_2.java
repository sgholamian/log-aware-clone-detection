//,temp,sample_4049.java,2,15,temp,sample_5188.java,2,10
//,3
public class xxx {
public static void moveAcidFiles(FileSystem fs, FileStatus[] stats, Path dst, List<Path> newFiles) throws HiveException {
Set<Path> createdDeltaDirs = new HashSet<Path>();
for (FileStatus stat : stats) {
Path srcPath = stat.getPath();


log.info("acid move looking for original buckets in");
}
}

};