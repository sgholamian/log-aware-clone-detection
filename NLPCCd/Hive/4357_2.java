//,temp,sample_3122.java,2,18,temp,sample_3123.java,2,18
//,3
public class xxx {
public void dummy_method(){
PrimitiveTypeInfo priti = (PrimitiveTypeInfo) ti;
PrimitiveTypeInfo descti = (PrimitiveTypeInfo) desc.getTypeInfo();
if (unSupportedTypes.contains(priti.getPrimitiveCategory()) || unSupportedTypes.contains(descti.getPrimitiveCategory())) {
if (LOG.isDebugEnabled()) {
}
return null;
}
boolean brokenDataTypesCombination = unsafeConversionTypes.contains( priti.getPrimitiveCategory()) && !unsafeConversionTypes.contains( descti.getPrimitiveCategory());
if (performSafeTypeCast && brokenDataTypesCombination) {
if (LOG.isDebugEnabled()) {


log.info("unsupported cast");
}
}
}

};