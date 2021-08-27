//,temp,SmppSubmitSmCommand.java,44,91,temp,SmppSubmitMultiCommand.java,50,121
//,3
public class xxx {
    @Override
    public void execute(Exchange exchange) throws SmppException {
        SubmitMulti[] submitMulties = createSubmitMulti(exchange);
        List<SubmitMultiResult> results = new ArrayList<>(submitMulties.length);

        for (SubmitMulti submitMulti : submitMulties) {
            SubmitMultiResult result;
            if (log.isDebugEnabled()) {
                log.debug("Sending multiple short messages for exchange id '{}'...", exchange.getExchangeId());
            }

            try {
                result = session.submitMultiple(
                        submitMulti.getServiceType(),
                        TypeOfNumber.valueOf(submitMulti.getSourceAddrTon()),
                        NumberingPlanIndicator.valueOf(submitMulti.getSourceAddrNpi()),
                        submitMulti.getSourceAddr(),
                        (Address[]) submitMulti.getDestAddresses(),
                        new ESMClass(submitMulti.getEsmClass()),
                        submitMulti.getProtocolId(),
                        submitMulti.getPriorityFlag(),
                        submitMulti.getScheduleDeliveryTime(),
                        submitMulti.getValidityPeriod(),
                        new RegisteredDelivery(submitMulti.getRegisteredDelivery()),
                        new ReplaceIfPresentFlag(submitMulti.getReplaceIfPresentFlag()),
                        DataCodings.newInstance(submitMulti.getDataCoding()),
                        submitMulti.getSmDefaultMsgId(),
                        submitMulti.getShortMessage(),
                        submitMulti.getOptionalParameters());
                results.add(result);
            } catch (Exception e) {
                throw new SmppException(e);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Sent multiple short messages for exchange id '{}' and received results '{}'", exchange.getExchangeId(),
                    results);
        }

        List<String> messageIDs = new ArrayList<>(results.size());
        // {messageID : [{destAddr : address, error : errorCode}]}
        Map<String, List<Map<String, Object>>> errors = new HashMap<>();

        for (SubmitMultiResult result : results) {
            UnsuccessDelivery[] deliveries = result.getUnsuccessDeliveries();

            if (deliveries != null) {
                List<Map<String, Object>> undelivered = new ArrayList<>();

                for (UnsuccessDelivery delivery : deliveries) {
                    Map<String, Object> error = new HashMap<>();
                    error.put(SmppConstants.DEST_ADDR, delivery.getDestinationAddress().getAddress());
                    error.put(SmppConstants.ERROR, delivery.getErrorStatusCode());
                    undelivered.add(error);
                }

                if (!undelivered.isEmpty()) {
                    errors.put(result.getMessageId(), undelivered);
                }
            }

            messageIDs.add(result.getMessageId());
        }

        Message message = getResponseMessage(exchange);
        message.setHeader(SmppConstants.ID, messageIDs);
        message.setHeader(SmppConstants.SENT_MESSAGE_COUNT, messageIDs.size());
        if (!errors.isEmpty()) {
            message.setHeader(SmppConstants.ERROR, errors);
        }
    }

};