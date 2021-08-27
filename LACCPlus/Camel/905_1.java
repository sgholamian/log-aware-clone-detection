//,temp,GoogleMailStreamConsumer.java,166,178,temp,GoogleMailStreamConsumer.java,141,161
//,3
public class xxx {
    protected void processRollback(Exchange exchange, String unreadLabelId) {
        try {
            LOG.warn("Exchange failed, so rolling back mail {} to un {}", exchange, unreadLabelId);

            List<String> add = new ArrayList<>();
            add.add(unreadLabelId);
            ModifyMessageRequest mods = new ModifyMessageRequest().setAddLabelIds(add);
            getClient().users().messages()
                    .modify("me", exchange.getIn().getHeader(GoogleMailStreamConstants.MAIL_ID, String.class), mods).execute();
        } catch (Exception e) {
            getExceptionHandler().handleException("Error occurred mark as read mail. This exception is ignored.", exchange, e);
        }
    }

};