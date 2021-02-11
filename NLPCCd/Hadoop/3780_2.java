//,temp,sample_7892.java,2,7,temp,sample_7872.java,2,8
//,3
public class xxx {
private void sortTest(FileSystem fs, int count, int megabytes, int factor, boolean fast, Path file) throws IOException {
fs.delete(new Path(file+".sorted"), true);
SequenceFile.Sorter sorter = newSorter(fs, fast, megabytes, factor);


log.info("sorting records");
}

};