//,temp,sample_3914.java,2,16,temp,sample_3915.java,2,14
//,3
public class xxx {
public void dummy_method(){
writeTextFile(dir2_file2, "dir2_file2_line1\n" + "dir2_file2_line2\n");
symbolLinkedFileSize += fs.getFileStatus(dir2_file2).getLen();
writeSymlinkFile( new Path(symlinkDir, "symlink_file"), new Path(dataDir1, "file1"), new Path(dataDir2, "file2"));
SymlinkTextInputFormat inputFormat = new SymlinkTextInputFormat();
ContentSummary cs = inputFormat.getContentSummary(symlinkDir, job);
assertEquals(symbolLinkedFileSize, cs.getLength());
assertEquals(2, cs.getFileCount());
assertEquals(0, cs.getDirectoryCount());
FileInputFormat.setInputPaths(job, symlinkDir);
InputSplit[] splits = inputFormat.getSplits(job, 2);


log.info("number of splits");
}

};