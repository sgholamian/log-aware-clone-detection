//,temp,sample_6994.java,2,13,temp,sample_6993.java,2,13
//,2
public class xxx {
public boolean syncProject() throws Exception {
final ApiConnector api = _manager.getApiConnector();
try {
List<?> dbList = _projectDao.listAll();
List<?> vncList = api.list(net.juniper.contrail.api.types.Project.class, null);
return _dbSync.syncGeneric(net.juniper.contrail.api.types.Project.class, dbList, vncList);
} catch (Exception ex) {


log.info("syncProject");
}
}

};