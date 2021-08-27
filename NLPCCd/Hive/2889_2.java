//,temp,sample_2000.java,2,18,temp,sample_2001.java,2,19
//,3
public class xxx {
public void dummy_method(){
long current = System.currentTimeMillis();
for (HiveSession session : new ArrayList<HiveSession>(handleToSession.values())) {
if (shutdown) {
break;
}
if (sessionTimeout > 0 && session.getLastAccessTime() + sessionTimeout <= current && (!checkOperation || session.getNoOperationTime() > sessionTimeout)) {
SessionHandle handle = session.getSessionHandle();
try {
closeSession(handle);
} catch (HiveSQLException e) {


log.info("exception is thrown closing session");
}
}
}
}

};