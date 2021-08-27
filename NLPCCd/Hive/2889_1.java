//,temp,sample_2000.java,2,18,temp,sample_2001.java,2,19
//,3
public class xxx {
public void run() {
sleepFor(interval);
while (!shutdown) {
long current = System.currentTimeMillis();
for (HiveSession session : new ArrayList<HiveSession>(handleToSession.values())) {
if (shutdown) {
break;
}
if (sessionTimeout > 0 && session.getLastAccessTime() + sessionTimeout <= current && (!checkOperation || session.getNoOperationTime() > sessionTimeout)) {
SessionHandle handle = session.getSessionHandle();


log.info("session is timed out last access and will be closed");
}
}
}
}

};