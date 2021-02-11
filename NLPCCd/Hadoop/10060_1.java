//,temp,sample_739.java,2,18,temp,sample_740.java,2,18
//,3
public class xxx {
public void dummy_method(){
FixedLengthInputFormat format = new FixedLengthInputFormat();
format.setRecordLength(job, 0);
format.configure(job);
InputSplit splits[] = format.getSplits(job, 1);
boolean exceptionThrown = false;
for (InputSplit split : splits) {
try {
RecordReader<LongWritable, BytesWritable> reader = format.getRecordReader(split, job, voidReporter);
} catch(IOException ioe) {
exceptionThrown = true;


log.info("exception message");
}
}
}

};