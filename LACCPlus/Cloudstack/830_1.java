//,temp,ConsoleProxyManagerImpl.java,1163,1176,temp,ConsoleProxyManagerImpl.java,1129,1143
//,3
public class xxx {
    private ConsoleProxyManagementState getLastManagementState() {
        String value = _configDao.getValue(Config.ConsoleProxyManagementLastState.key());
        if (value != null) {
            ConsoleProxyManagementState state = ConsoleProxyManagementState.valueOf(value);

            if (state == null) {
                s_logger.error("Invalid console proxy management state: " + value);
            }
            return state;
        }

        s_logger.error("Invalid console proxy management state: " + value);
        return null;
    }

};