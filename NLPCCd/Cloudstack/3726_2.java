//,temp,sample_6994.java,2,13,temp,sample_6993.java,2,13
//,2
public class xxx {
public boolean syncDomain() throws Exception {
final ApiConnector api = _manager.getApiConnector();
try {
List<?> dbList = _domainDao.listAll();
List<?> vncList = api.list(net.juniper.contrail.api.types.Domain.class, null);
return _dbSync.syncGeneric(net.juniper.contrail.api.types.Domain.class, dbList, vncList);
} catch (Exception ex) {


log.info("syncDomain");
}
}

};