//,temp,sample_6938.java,2,12,temp,sample_6942.java,2,13
//,3
public class xxx {
private ConsoleProxyManagementState getLastManagementState() {
String value = _configDao.getValue(Config.ConsoleProxyManagementLastState.key());
if (value != null) {
ConsoleProxyManagementState state = ConsoleProxyManagementState.valueOf(value);
if (state == null) {
}
return state;
}


log.info("invalid console proxy management state");
}

};