//,temp,sample_67.java,2,18,temp,sample_4723.java,2,18
//,3
public class xxx {
public void dummy_method(){
if(sourceConnection != null){
try {
sourceConnection.close();
} catch (Exception e) {
}
}
if(replicatedTable != null){
try{
replicatedTable.close();
} catch (Exception e) {


log.info("fail to close replicated table in cleanup");
}
}
}

};