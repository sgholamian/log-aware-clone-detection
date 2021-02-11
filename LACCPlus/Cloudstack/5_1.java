//,temp,LdapManagerImpl.java,155,163,temp,RabbitMQEventBus.java,408,418
//,3
public class xxx {
    private void closeContext(final LdapContext context) {
        try {
            if (context != null) {
                context.close();
            }
        } catch (final NamingException e) {
            s_logger.warn(e.getMessage(), e);
        }
    }

};