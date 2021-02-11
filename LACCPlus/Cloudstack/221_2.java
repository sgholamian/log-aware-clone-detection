//,temp,CallContext.java,159,182,temp,LogContext.java,129,142
//,3
public class xxx {
    protected static LogContext register(User callingUser, Account callingAccount, Long userId, Long accountId, String contextId) {
        LogContext callingContext = null;
        if (userId == null || accountId == null) {
            callingContext = new LogContext(callingUser, callingAccount, contextId);
        } else {
            callingContext = new LogContext(userId, accountId, contextId);
        }
        s_currentContext.set(callingContext);
        MDC.put("logcontextid", UuidUtils.first(contextId));
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("Registered for log: " + callingContext);
        }
        return callingContext;
    }

};