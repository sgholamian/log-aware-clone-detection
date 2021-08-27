//,temp,sample_4812.java,2,9,temp,sample_4813.java,2,12
//,3
public class xxx {
private void validateOrder(Message order) throws IncorrectTagValue, FieldNotFound {
OrdType ordType = new OrdType(order.getChar(OrdType.FIELD));
if (!validOrderTypes.contains(Character.toString(ordType.getValue()))) {
throw new IncorrectTagValue(ordType.getField());
}
if (ordType.getValue() == OrdType.MARKET && marketQuoteProvider == null) {


log.info("defaultmarketprice setting not specified for market order");
}
}

};