//,temp,sample_3012.java,2,18,temp,sample_4724.java,2,18
//,2
public class xxx {
public void dummy_method(){
if(replicatedTable != null){
try{
replicatedTable.close();
} catch (Exception e) {
}
}
if(replicatedConnection != null){
try {
replicatedConnection.close();
} catch (Exception e) {


log.info("fail to close replicated connection in cleanup");
}
}
}

};