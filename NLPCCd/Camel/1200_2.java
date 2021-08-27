//,temp,sample_237.java,2,16,temp,sample_4787.java,2,17
//,3
public class xxx {
public void dummy_method(){
ObjectHelper.notNull(levelDBFile, "Either set a persistentFileName or a levelDBFile");
ObjectHelper.notNull(repositoryName, "repositoryName");
ServiceHelper.startService(levelDBFile);
int current = size(getRepositoryName());
int completed = size(getRepositoryNameCompleted());
if (current > 0) {
} else {
}
if (completed > 0) {
} else {


log.info("on startup there are no completed exchanges to be recovered in repository");
}
}

};