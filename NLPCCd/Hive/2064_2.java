//,temp,sample_787.java,2,16,temp,sample_786.java,2,16
//,2
public class xxx {
public void dummy_method(){
Properties tblProps = e.first;
HCatRecord r = e.second;
Properties internalTblProps = new Properties();
for (Map.Entry pe : tblProps.entrySet()) {
if (!pe.getKey().equals(serdeConstants.LIST_COLUMNS)) {
internalTblProps.put(pe.getKey(), pe.getValue());
} else {
internalTblProps.put(pe.getKey(), getInternalNames((String) pe.getValue()));
}
}


log.info("orig tbl props");
}

};