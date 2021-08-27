//,temp,sample_3215.java,2,9,temp,sample_205.java,2,9
//,3
public class xxx {
public static void createMRWorkForMergingFiles(FileSinkOperator fsInput, Path finalName, DependencyCollectionTask dependencyTask, List<Task<MoveWork>> mvTasks, HiveConf conf, Task<? extends Serializable> currTask, LineageState lineageState) throws SemanticException {
FileSinkDesc fsInputDesc = fsInput.getConf();
if (Utilities.FILE_OP_LOGGER.isTraceEnabled()) {


log.info("creating merge work from with write id into");
}
}

};