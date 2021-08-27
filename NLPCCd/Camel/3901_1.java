//,temp,sample_7695.java,2,16,temp,sample_1691.java,2,17
//,3
public class xxx {
public void execute(Exchange exchange) throws SmppException {
CancelSm cancelSm = createCancelSm(exchange);
if (log.isDebugEnabled()) {
}
try {
session.cancelShortMessage( cancelSm.getServiceType(), cancelSm.getMessageId(), TypeOfNumber.valueOf(cancelSm.getSourceAddrTon()), NumberingPlanIndicator.valueOf(cancelSm.getSourceAddrNpi()), cancelSm.getSourceAddr(), TypeOfNumber.valueOf(cancelSm.getDestAddrTon()), NumberingPlanIndicator.valueOf(cancelSm.getDestAddrNpi()), cancelSm.getDestinationAddress());
} catch (Exception e) {
throw new SmppException(e);
}
if (log.isDebugEnabled()) {


log.info("cancel a short message for exchange id and message id");
}
}

};