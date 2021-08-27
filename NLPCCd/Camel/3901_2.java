//,temp,sample_7695.java,2,16,temp,sample_1691.java,2,17
//,3
public class xxx {
public void dummy_method(){
DataSm dataSm = createDataSm(exchange);
if (log.isDebugEnabled()) {
}
DataSmResult result;
try {
result = session.dataShortMessage( dataSm.getServiceType(), TypeOfNumber.valueOf(dataSm.getSourceAddrTon()), NumberingPlanIndicator.valueOf(dataSm.getSourceAddrNpi()), dataSm.getSourceAddr(), TypeOfNumber.valueOf(dataSm.getDestAddrTon()), NumberingPlanIndicator.valueOf(dataSm.getDestAddrNpi()), dataSm.getDestAddress(), new ESMClass(dataSm.getEsmClass()), new RegisteredDelivery(dataSm.getRegisteredDelivery()), DataCodings.newInstance(dataSm.getDataCoding()), dataSm.getOptionalParameters());
} catch (Exception e) {
throw new SmppException(e);
}
if (log.isDebugEnabled()) {


log.info("sent a data short message for exchange id and message id");
}
}

};