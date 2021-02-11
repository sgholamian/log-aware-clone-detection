//,temp,sample_7433.java,2,9,temp,sample_7432.java,2,9
//,2
public class xxx {
public UserDataServiceProvider getSSHKeyResetProvider(final Network network) {
final String SSHKeyProvider = _ntwkSrvcDao.getProviderForServiceInNetwork(network.getId(), Service.UserData);
if (SSHKeyProvider == null) {


log.info("network doesn t support service");
}
}

};