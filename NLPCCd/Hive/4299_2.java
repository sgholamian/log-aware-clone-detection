//,temp,sample_3914.java,2,16,temp,sample_3915.java,2,14
//,3
public class xxx {
public void testAccuracy2() throws IOException {
fileSystem.mkdirs(symlinkDir);
FileInputFormat.setInputPaths(job, symlinkDir);
SymlinkTextInputFormat inputFormat = new SymlinkTextInputFormat();
ContentSummary cs = inputFormat.getContentSummary(symlinkDir, job);
assertEquals(0, cs.getLength());
assertEquals(0, cs.getFileCount());
assertEquals(0, cs.getDirectoryCount());
InputSplit[] splits = inputFormat.getSplits(job, 2);


log.info("number of splits");
}

};