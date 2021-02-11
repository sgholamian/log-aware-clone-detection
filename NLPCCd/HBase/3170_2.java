//,temp,sample_2988.java,2,18,temp,sample_4722.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (sourceTable != null) {
try {
sourceTable.close();
} catch (IOException e) {
}
}
if(sourceConnection != null){
try {
sourceConnection.close();
} catch (Exception e) {


log.info("fail to close source connection in cleanup");
}
}
}

};