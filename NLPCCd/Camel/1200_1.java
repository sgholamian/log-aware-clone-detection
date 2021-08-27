//,temp,sample_237.java,2,16,temp,sample_4787.java,2,17
//,3
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(repositoryName, "RepositoryName");
ObjectHelper.notNull(transactionManager, "TransactionManager");
ObjectHelper.notNull(dataSource, "DataSource");
int current = getKeys().size();
int completed = scan(null).size();
if (current > 0) {
} else {
}
if (completed > 0) {


log.info("on startup there are completed exchanges to be recovered in repository");
}
}

};