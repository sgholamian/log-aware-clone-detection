//,temp,sample_2675.java,2,11,temp,sample_9007.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (sharedEditsImage != null) {
try {
sharedEditsImage.close();
}  catch (IOException ioe) {
}
}
if (existingStorage != null) {
try {
existingStorage.unlockAll();
} catch (IOException ioe) {


log.info("could not unlock storage directories");
}
}
}

};