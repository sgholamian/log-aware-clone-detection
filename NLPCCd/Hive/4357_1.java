//,temp,sample_3122.java,2,18,temp,sample_3123.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (desc instanceof ExprNodeConstantDesc && null == ((ExprNodeConstantDesc)desc).getValue()) {
return null;
}
if (!(ti instanceof PrimitiveTypeInfo) || !(desc.getTypeInfo() instanceof PrimitiveTypeInfo)) {
return null;
}
PrimitiveTypeInfo priti = (PrimitiveTypeInfo) ti;
PrimitiveTypeInfo descti = (PrimitiveTypeInfo) desc.getTypeInfo();
if (unSupportedTypes.contains(priti.getPrimitiveCategory()) || unSupportedTypes.contains(descti.getPrimitiveCategory())) {
if (LOG.isDebugEnabled()) {


log.info("unsupported types");
}
}
}

};