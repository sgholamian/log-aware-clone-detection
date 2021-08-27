//,temp,sample_5912.java,2,17,temp,sample_238.java,2,17
//,3
public class xxx {
public void dummy_method(){
ObjectHelper.notNull(repositoryName, "RepositoryName");
ObjectHelper.notNull(transactionManager, "TransactionManager");
ObjectHelper.notNull(dataSource, "DataSource");
int current = getKeys().size();
int completed = scan(null).size();
if (current > 0) {
} else {
}
if (completed > 0) {
} else {


log.info("on startup there are no completed exchanges to be recovered in repository");
}
}

};