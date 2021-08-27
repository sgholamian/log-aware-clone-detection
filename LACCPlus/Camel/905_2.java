//,temp,GoogleMailStreamConsumer.java,166,178,temp,GoogleMailStreamConsumer.java,141,161
//,3
public class xxx {
    protected void processCommit(Exchange exchange, String unreadLabelId) {
        try {
            if (getConfiguration().isMarkAsRead()) {
                String id = exchange.getIn().getHeader(GoogleMailStreamConstants.MAIL_ID, String.class);

                LOG.trace("Marking email {} as read", id);

                List<String> remove = new ArrayList<>();
                remove.add(unreadLabelId);
                ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(remove);
                getClient().users().messages()
                        .modify("me", exchange.getIn().getHeader(GoogleMailStreamConstants.MAIL_ID, String.class), mods)
                        .execute();

                LOG.trace("Marked email {} as read", id);
            }
        } catch (Exception e) {
            getExceptionHandler().handleException("Error occurred mark as read mail. This exception is ignored.", exchange, e);
        }

    }

};