//,temp,sample_8675.java,2,12,temp,sample_8679.java,2,13
//,3
public class xxx {
public void testMD5Filter() throws Exception {
SequenceFileInputFilter.setFilterClass(job, SequenceFileInputFilter.MD5Filter.class);
SequenceFileInputFilter.MD5Filter.setFrequency(job, 1000);
fs.delete(inDir, true);
for (int length = 0; length < MAX_LENGTH;
length+= random.nextInt(MAX_LENGTH/10)+1) {
createSequenceFile(length);


log.info("accepted records");
}
}

};