//,temp,sample_7432.java,2,9,temp,sample_7431.java,2,9
//,2
public class xxx {
public UserDataServiceProvider getPasswordResetProvider(final Network network) {
final String passwordProvider = _ntwkSrvcDao.getProviderForServiceInNetwork(network.getId(), Service.UserData);
if (passwordProvider == null) {


log.info("network doesn t support service");
}
}

};