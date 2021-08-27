//,temp,sample_3011.java,2,18,temp,sample_3012.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (partDesc == null) {
return new DeserializerOrcWriter(serDe, sourceOi, allocSize);
}
Properties tblProps = partDesc.getTableDesc().getProperties();
if ("true".equalsIgnoreCase(tblProps.getProperty( serdeConstants.SERIALIZATION_LAST_COLUMN_TAKES_REST))) {
return new DeserializerOrcWriter(serDe, sourceOi, allocSize);
}
for (StructField sf : sourceOi.getAllStructFieldRefs()) {
Category c = sf.getFieldObjectInspector().getCategory();
if (c != Category.PRIMITIVE) {


log.info("not using vertordeserializeorcwriter is not supported");
}
}
}

};