//,temp,sample_4723.java,2,18,temp,sample_3873.java,2,13
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