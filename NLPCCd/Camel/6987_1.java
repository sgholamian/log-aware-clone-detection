//,temp,sample_4812.java,2,9,temp,sample_4813.java,2,12
//,3
public class xxx {
private void validateOrder(Message order) throws IncorrectTagValue, FieldNotFound {
OrdType ordType = new OrdType(order.getChar(OrdType.FIELD));
if (!validOrderTypes.contains(Character.toString(ordType.getValue()))) {


log.info("order type not in validordertypes setting");
}
}

};