//,temp,sample_3421.java,2,17,temp,sample_1984.java,2,17
//,3
public class xxx {
public void dummy_method(){
Map<ColumnInfo, ExprNodeDesc> constMap = opToConstantExprs.get(parent);
if (constMap == null) {
areAllParentsContainConstant = false;
} else {
noParentsContainConstant = false;
Map<Integer, ExprNodeDesc> map = new HashMap<>();
for (Entry<ColumnInfo, ExprNodeDesc> entry : constMap.entrySet()) {
map.put(parent.getSchema().getPosition(entry.getKey().getInternalName()), entry.getValue());
}
parentsToConstant.add(map);


log.info("constant of op");
}
}

};