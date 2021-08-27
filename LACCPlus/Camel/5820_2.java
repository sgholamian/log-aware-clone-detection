//,temp,QuickfixjEngine.java,346,364,temp,QuickfixjEngine.java,310,326
//,3
public class xxx {
    private MessageStoreFactory inferMessageStoreFactory(SessionSettings settings) throws ConfigError {
        Set<MessageStoreFactory> impliedMessageStoreFactories = new HashSet<>();
        isJdbcStore(settings, impliedMessageStoreFactories);
        isFileStore(settings, impliedMessageStoreFactories);
        isSleepycatStore(settings, impliedMessageStoreFactories);
        if (impliedMessageStoreFactories.size() > 1) {
            throw new ConfigError("Ambiguous message store implied in configuration.");
        }
        MessageStoreFactory messageStoreFactory;
        if (impliedMessageStoreFactories.size() == 1) {
            messageStoreFactory = impliedMessageStoreFactories.iterator().next();
        } else {
            messageStoreFactory = new MemoryStoreFactory();
        }
        LOG.info("Inferring message store factory: {}", messageStoreFactory.getClass().getName());
        return messageStoreFactory;
    }

};