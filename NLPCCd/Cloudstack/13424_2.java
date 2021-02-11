//,temp,sample_6288.java,2,17,temp,sample_4585.java,2,16
//,3
public class xxx {
public ServerResource reloadResource(HostVO host) {
String resourceName = host.getResource();
ServerResource resource = getResource(resourceName);
if (resource != null) {
_hostDao.loadDetails(host);
HashMap<String, Object> params = buildConfigParams(host);
try {
resource.configure(host.getName(), params);
} catch (ConfigurationException e) {


log.info("unable to configure resource due to");
}
}
}

};