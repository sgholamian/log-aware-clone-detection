//,temp,sample_6938.java,2,12,temp,sample_6942.java,2,13
//,3
public class xxx {
public ConsoleProxyManagementState getManagementState() {
String value = _configDao.getValue(Config.ConsoleProxyManagementState.key());
if (value != null) {
ConsoleProxyManagementState state = ConsoleProxyManagementState.valueOf(value);
if (state == null) {


log.info("invalid console proxy management state");
}
}
}

};