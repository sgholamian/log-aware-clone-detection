//,temp,sample_2503.java,2,7,temp,sample_2510.java,2,7
//,3
public class xxx {
public GetInfoValue getInfo(SessionHandle sessionHandle, GetInfoType getInfoType) throws HiveSQLException {
GetInfoValue infoValue = sessionManager.getSession(sessionHandle) .getInfo(getInfoType);


log.info("getinfo");
}

};