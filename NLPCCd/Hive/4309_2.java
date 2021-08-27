//,temp,sample_3745.java,2,17,temp,sample_3744.java,2,18
//,3
public class xxx {
public void dummy_method(){
FetchTask fetchTask = plan.getFetchTask();
if (fetchTask != null) {
SessionState ss = SessionState.get();
SessionState.LogHelper console = ss.getConsole();
PrintStream old = System.out;
System.setOut(console.getErrStream());
FetchWork fetchWork = fetchTask.getWork();
boolean partitionedTable = fetchWork.isPartitioned();
List<Path> directories;
if (partitionedTable) {


log.info("printing orc file dump for files from partitioned directory");
}
}
}

};