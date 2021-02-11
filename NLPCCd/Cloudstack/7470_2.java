//,temp,sample_7431.java,2,9,temp,sample_2469.java,2,9
//,2
public class xxx {
public UserDataServiceProvider getUserDataUpdateProvider(Network network) {
String userDataProvider = _ntwkSrvcDao.getProviderForServiceInNetwork(network.getId(), Service.UserData);
if (userDataProvider == null) {


log.info("network doesn t support service");
}
}

};