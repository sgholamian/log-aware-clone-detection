//,temp,SmppSubmitSmCommand.java,44,91,temp,SmppSubmitMultiCommand.java,50,121
//,3
public class xxx {
    @Override
    public void execute(Exchange exchange) throws SmppException {
        SubmitSm[] submitSms = createSubmitSm(exchange);
        List<String> messageIDs = new ArrayList<>(submitSms.length);

        for (int i = 0; i < submitSms.length; i++) {
            SubmitSm submitSm = submitSms[i];
            String messageID;
            if (log.isDebugEnabled()) {
                log.debug("Sending short message {} for exchange id '{}'...", i, exchange.getExchangeId());
            }

            try {
                messageID = session.submitShortMessage(
                        submitSm.getServiceType(),
                        TypeOfNumber.valueOf(submitSm.getSourceAddrTon()),
                        NumberingPlanIndicator.valueOf(submitSm.getSourceAddrNpi()),
                        submitSm.getSourceAddr(),
                        TypeOfNumber.valueOf(submitSm.getDestAddrTon()),
                        NumberingPlanIndicator.valueOf(submitSm.getDestAddrNpi()),
                        submitSm.getDestAddress(),
                        new ESMClass(submitSm.getEsmClass()),
                        submitSm.getProtocolId(),
                        submitSm.getPriorityFlag(),
                        submitSm.getScheduleDeliveryTime(),
                        submitSm.getValidityPeriod(),
                        new RegisteredDelivery(submitSm.getRegisteredDelivery()),
                        submitSm.getReplaceIfPresent(),
                        DataCodings.newInstance(submitSm.getDataCoding()),
                        (byte) 0,
                        submitSm.getShortMessage(),
                        submitSm.getOptionalParameters());
            } catch (Exception e) {
                throw new SmppException(e);
            }

            messageIDs.add(messageID);
        }

        if (log.isDebugEnabled()) {
            log.debug("Sent short message for exchange id '{}' and received message ids '{}'",
                    exchange.getExchangeId(), messageIDs);
        }

        Message message = getResponseMessage(exchange);
        message.setHeader(SmppConstants.ID, messageIDs);
        message.setHeader(SmppConstants.SENT_MESSAGE_COUNT, messageIDs.size());
    }

};