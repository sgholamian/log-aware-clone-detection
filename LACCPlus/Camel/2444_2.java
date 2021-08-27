//,temp,SmppCancelSmCommand.java,32,62,temp,SmppDataSmCommand.java,48,83
//,3
public class xxx {
    @Override
    public void execute(Exchange exchange) throws SmppException {
        DataSm dataSm = createDataSm(exchange);

        if (log.isDebugEnabled()) {
            log.debug("Sending a data short message for exchange id '{}'...", exchange.getExchangeId());
        }

        DataSmResult result;
        try {
            result = session.dataShortMessage(
                    dataSm.getServiceType(),
                    TypeOfNumber.valueOf(dataSm.getSourceAddrTon()),
                    NumberingPlanIndicator.valueOf(dataSm.getSourceAddrNpi()),
                    dataSm.getSourceAddr(),
                    TypeOfNumber.valueOf(dataSm.getDestAddrTon()),
                    NumberingPlanIndicator.valueOf(dataSm.getDestAddrNpi()),
                    dataSm.getDestAddress(),
                    new ESMClass(dataSm.getEsmClass()),
                    new RegisteredDelivery(dataSm.getRegisteredDelivery()),
                    DataCodings.newInstance(dataSm.getDataCoding()),
                    dataSm.getOptionalParameters());
        } catch (Exception e) {
            throw new SmppException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("Sent a data short message for exchange id '{}' and message id '{}'",
                    exchange.getExchangeId(), result.getMessageId());
        }

        Message message = getResponseMessage(exchange);
        message.setHeader(SmppConstants.ID, result.getMessageId());
        message.setHeader(SmppConstants.OPTIONAL_PARAMETERS, createOptionalParameterByName(result.getOptionalParameters()));
        message.setHeader(SmppConstants.OPTIONAL_PARAMETER, createOptionalParameterByCode(result.getOptionalParameters()));
    }

};