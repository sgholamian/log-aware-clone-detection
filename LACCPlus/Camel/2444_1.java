//,temp,SmppCancelSmCommand.java,32,62,temp,SmppDataSmCommand.java,48,83
//,3
public class xxx {
    @Override
    public void execute(Exchange exchange) throws SmppException {
        CancelSm cancelSm = createCancelSm(exchange);

        if (log.isDebugEnabled()) {
            log.debug("Canceling a short message for exchange id '{}' and message id '{}'",
                    exchange.getExchangeId(), cancelSm.getMessageId());
        }

        try {
            session.cancelShortMessage(
                    cancelSm.getServiceType(),
                    cancelSm.getMessageId(),
                    TypeOfNumber.valueOf(cancelSm.getSourceAddrTon()),
                    NumberingPlanIndicator.valueOf(cancelSm.getSourceAddrNpi()),
                    cancelSm.getSourceAddr(),
                    TypeOfNumber.valueOf(cancelSm.getDestAddrTon()),
                    NumberingPlanIndicator.valueOf(cancelSm.getDestAddrNpi()),
                    cancelSm.getDestinationAddress());
        } catch (Exception e) {
            throw new SmppException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("Cancel a short message for exchange id '{}' and message id '{}'",
                    exchange.getExchangeId(), cancelSm.getMessageId());
        }

        Message message = getResponseMessage(exchange);
        message.setHeader(SmppConstants.ID, cancelSm.getMessageId());
    }

};