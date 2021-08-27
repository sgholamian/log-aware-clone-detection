//,temp,sample_2844.java,2,19,temp,sample_2845.java,2,17
//,3
public class xxx {
public void dummy_method(){
for (FileStatus dir : dirsInLoadPath){
String locn = dir.getPath().toUri().toString();
DumpMetaData eventDmd = new DumpMetaData(new Path(locn), conf);
MessageHandler.Context context = new MessageHandler.Context(dbNameOrPattern, tblNameOrPattern, locn, taskChainTail, eventDmd, conf, db, ctx, LOG);
List<Task<? extends Serializable>> evTasks = analyzeEventLoad(context);
if ((evTasks != null) && (!evTasks.isEmpty())){
ReplStateLogWork replStateLogWork = new ReplStateLogWork(replLogger, dir.getPath().getName(), eventDmd.getDumpType().toString());
Task<? extends Serializable> barrierTask = TaskFactory.get(replStateLogWork, conf);
for (Task<? extends Serializable> t : evTasks){
t.addDependentTask(barrierTask);


log.info("added as a precursor of barrier task");
}
}
}
}

};