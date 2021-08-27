//,temp,sample_5912.java,2,17,temp,sample_238.java,2,17
//,3
public class xxx {
public void dummy_method(){
ObjectHelper.notNull(hawtDBFile, "Either set a persistentFileName or a hawtDBFile");
ObjectHelper.notNull(repositoryName, "repositoryName");
ServiceHelper.startService(hawtDBFile);
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