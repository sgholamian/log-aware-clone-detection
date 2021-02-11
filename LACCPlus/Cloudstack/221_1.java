//,temp,CallContext.java,159,182,temp,LogContext.java,129,142
//,3
public class xxx {
    protected static CallContext register(User callingUser, Account callingAccount, Long userId, Long accountId, String contextId) {
        /*
                Unit tests will have multiple times of setup/tear-down call to this, remove assertions to all unit test to run
                assert s_currentContext.get() == null : "There's a context already so what does this new register context mean? " + s_currentContext.get().toString();
                if (s_currentContext.get() != null) { // FIXME: This should be removed soon.  I added this check only to surface all the places that have this problem.
                    throw new CloudRuntimeException("There's a context already so what does this new register context mean? " + s_currentContext.get().toString());
                }
        */
        CallContext callingContext = null;
        if (userId == null || accountId == null) {
            callingContext = new CallContext(callingUser, callingAccount, contextId);
        } else {
            callingContext = new CallContext(userId, accountId, contextId);
        }
        s_currentContext.set(callingContext);
        NDC.push("ctx-" + UuidUtils.first(contextId));
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("Registered: " + callingContext);
        }

        s_currentContextStack.get().push(callingContext);

        return callingContext;
    }

};