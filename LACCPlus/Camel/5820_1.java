//,temp,QuickfixjEngine.java,346,364,temp,QuickfixjEngine.java,310,326
//,3
public class xxx {
    private LogFactory inferLogFactory(SessionSettings settings) throws ConfigError {
        Set<LogFactory> impliedLogFactories = new HashSet<>();
        isFileLog(settings, impliedLogFactories);
        isScreenLog(settings, impliedLogFactories);
        isSL4JLog(settings, impliedLogFactories);
        isJdbcLog(settings, impliedLogFactories);
        if (impliedLogFactories.size() > 1) {
            throw new ConfigError("Ambiguous log factory implied in configuration");
        }
        LogFactory sessionLogFactory;
        if (impliedLogFactories.size() == 1) {
            sessionLogFactory = impliedLogFactories.iterator().next();
        } else {
            // Default
            sessionLogFactory = new ScreenLogFactory(settings);
        }
        LOG.info("Inferring log factory: {}", sessionLogFactory.getClass().getName());
        return sessionLogFactory;
    }

};