//,temp,sample_6939.java,2,13,temp,sample_6941.java,2,12
//,3
public class xxx {
public ConsoleProxyManagementState getManagementState() {
String value = _configDao.getValue(Config.ConsoleProxyManagementState.key());
if (value != null) {
ConsoleProxyManagementState state = ConsoleProxyManagementState.valueOf(value);
if (state == null) {
}
return state;
}


log.info("invalid console proxy management state");
}

};