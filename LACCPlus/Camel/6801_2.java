//,temp,SmppQuerySmCommand.java,33,63,temp,SmppReplaceSmCommand.java,35,69
//,3
public class xxx {
    @Override
    public void execute(Exchange exchange) throws SmppException {
        byte[] message = getShortMessage(exchange.getIn());

        ReplaceSm replaceSm = createReplaceSmTempate(exchange);
        replaceSm.setShortMessage(message);

        if (log.isDebugEnabled()) {
            log.debug("Sending replacement command for a short message for exchange id '{}' and message id '{}'",
                    exchange.getExchangeId(), replaceSm.getMessageId());
        }

        try {
            session.replaceShortMessage(
                    replaceSm.getMessageId(),
                    TypeOfNumber.valueOf(replaceSm.getSourceAddrTon()),
                    NumberingPlanIndicator.valueOf(replaceSm.getSourceAddrNpi()),
                    replaceSm.getSourceAddr(),
                    replaceSm.getScheduleDeliveryTime(),
                    replaceSm.getValidityPeriod(),
                    new RegisteredDelivery(replaceSm.getRegisteredDelivery()),
                    replaceSm.getSmDefaultMsgId(),
                    replaceSm.getShortMessage());
        } catch (Exception e) {
            throw new SmppException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("Sent replacement command for a short message for exchange id '{}' and message id '{}'",
                    exchange.getExchangeId(), replaceSm.getMessageId());
        }

        Message rspMsg = getResponseMessage(exchange);
        rspMsg.setHeader(SmppConstants.ID, replaceSm.getMessageId());
    }

};