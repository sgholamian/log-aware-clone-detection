//,temp,ServerCnxnFactory.java,76,87,temp,FileTxnLog.java,353,361
//,3
public class xxx {
    public boolean closeSession(long sessionId) {
        ServerCnxn cnxn = sessionMap.remove(sessionId);
        if (cnxn != null) {
            try {
                cnxn.close();
            } catch (Exception e) {
                LOG.warn("exception during session close", e);
            }
            return true;
        }
        return false;
    }

};