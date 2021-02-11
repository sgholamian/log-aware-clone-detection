//,temp,CallContext.java,196,208,temp,LogContext.java,154,166
//,2
public class xxx {
    public static LogContext registerSystemLogContextOnceOnly() {
        try {
            LogContext context = s_currentContext.get();
            if (context == null) {
                return register(null, null, User.UID_SYSTEM, Account.ACCOUNT_ID_SYSTEM, UUID.randomUUID().toString());
            }
            assert context.getCallingUserId() == User.UID_SYSTEM : "You are calling a very specific method that registers a one time system context.  This method is meant for background threads that does processing.";
            return context;
        } catch (Exception e) {
            s_logger.error("Failed to register the system log context.", e);
            throw new CloudRuntimeException("Failed to register system log context", e);
        }
    }

};