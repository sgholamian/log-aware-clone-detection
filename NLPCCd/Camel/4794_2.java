//,temp,sample_236.java,2,14,temp,sample_235.java,2,13
//,2
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(repositoryName, "RepositoryName");
ObjectHelper.notNull(transactionManager, "TransactionManager");
ObjectHelper.notNull(dataSource, "DataSource");
int current = getKeys().size();
int completed = scan(null).size();
if (current > 0) {


log.info("on startup there are aggregate exchanges not completed in repository");
}
}

};