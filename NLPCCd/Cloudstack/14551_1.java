//,temp,sample_7433.java,2,9,temp,sample_7432.java,2,9
//,2
public class xxx {
public DhcpServiceProvider getDhcpServiceProvider(final Network network) {
final String DhcpProvider = _ntwkSrvcDao.getProviderForServiceInNetwork(network.getId(), Service.Dhcp);
if (DhcpProvider == null) {


log.info("network doesn t support service");
}
}

};