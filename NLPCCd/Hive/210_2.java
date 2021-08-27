//,temp,sample_1978.java,2,18,temp,sample_1979.java,2,18
//,2
public class xxx {
public void dummy_method(){
JobConf newjob = new JobConf(job);
ArrayList<InputSplit> result = new ArrayList<InputSplit>();
int numOrigSplits = 0;
for (Path dir : dirs) {
PartitionDesc part = getPartitionDescFromPath(pathToPartitionInfo, dir);
Class inputFormatClass = part.getInputFileFormatClass();
InputFormat inputFormat = getInputFormatFromCache(inputFormatClass, job);
newjob.setInputFormat(inputFormat.getClass());
FileStatus[] listStatus = listStatus(newjob, dir);
for (FileStatus status : listStatus) {


log.info("file length");
}
}
}

};