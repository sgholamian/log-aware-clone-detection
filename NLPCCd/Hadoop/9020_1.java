//,temp,sample_6344.java,2,9,temp,sample_1608.java,2,11
//,3
public class xxx {
public void assignTo(TaskID taskId) throws IOException {
Path newPath = new Path(chunkContext.getChunkRootPath(), taskId.toString());
if (!chunkContext.getFs().rename(chunkFilePath, newPath)) {


log.info("could not be assigned to");
}
}

};