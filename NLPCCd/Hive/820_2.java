//,temp,sample_2883.java,2,18,temp,sample_641.java,2,18
//,3
public class xxx {
public void dummy_method(){
ImmutableBitSet.Builder keys = ImmutableBitSet.builder();
for (String pkColName : pki.getColNames().values()) {
int pkPos;
for (pkPos = 0; pkPos < rowType.getFieldNames().size(); pkPos++) {
String colName = rowType.getFieldNames().get(pkPos);
if (pkColName.equals(colName)) {
break;
}
}
if (pkPos == rowType.getFieldNames().size()) {


log.info("column for primary key definition not found");
}
}
}

};