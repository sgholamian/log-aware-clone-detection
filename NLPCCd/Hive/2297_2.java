//,temp,sample_2757.java,2,17,temp,sample_3061.java,2,17
//,3
public class xxx {
public void dummy_method(){
boolean result;
try {
if(purge) {
} else {
result = Trash.moveToAppropriateTrash(fs, f, conf);
if (result) {
return true;
}
}
} catch (IOException ioe) {


log.info("force to delete it");
}
}

};