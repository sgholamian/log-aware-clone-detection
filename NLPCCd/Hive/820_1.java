//,temp,sample_2883.java,2,18,temp,sample_641.java,2,18
//,3
public class xxx {
public void dummy_method(){
ImmutableBitSet.Builder builder = ImmutableBitSet.builder();
for (String nnCol : nnc.getNotNullConstraints().values()) {
int nnPos = -1;
for (int i = 0; i < rowType.getFieldNames().size(); i++) {
if (rowType.getFieldNames().get(i).equals(nnCol)) {
nnPos = i;
break;
}
}
if (nnPos == -1) {


log.info("column for not null constraint definition not found");
}
}
}

};