//,temp,SmppQuerySmCommand.java,33,63,temp,SmppReplaceSmCommand.java,35,69
//,3
public class xxx {
    @Override
    public void execute(Exchange exchange) throws SmppException {
        QuerySm querySm = createQuerySm(exchange);

        if (log.isDebugEnabled()) {
            log.debug("Querying for a short message for exchange id '{}' and message id '{}'...",
                    exchange.getExchangeId(), querySm.getMessageId());
        }

        QuerySmResult querySmResult;
        try {
            querySmResult = session.queryShortMessage(
                    querySm.getMessageId(),
                    TypeOfNumber.valueOf(querySm.getSourceAddrTon()),
                    NumberingPlanIndicator.valueOf(querySm.getSourceAddrNpi()),
                    querySm.getSourceAddr());
        } catch (Exception e) {
            throw new SmppException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("Query for a short message for exchange id '{}' and message id '{}'",
                    exchange.getExchangeId(), querySm.getMessageId());
        }

        Message message = getResponseMessage(exchange);
        message.setHeader(SmppConstants.ID, querySm.getMessageId());
        message.setHeader(SmppConstants.ERROR, querySmResult.getErrorCode());
        message.setHeader(SmppConstants.FINAL_DATE, SmppUtils.string2Date(querySmResult.getFinalDate()));
        message.setHeader(SmppConstants.MESSAGE_STATE, querySmResult.getMessageState().name());
    }

};