//,temp,sample_2259.java,2,14,temp,sample_4817.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (FileStatus file : filesToDelete) {
Path filePath = file.getPath();
try {
boolean success = this.fs.delete(filePath, false);
if (success) {
deletedFileCount++;
} else {
}
} catch (IOException e) {
e = e instanceof RemoteException ? ((RemoteException)e).unwrapRemoteException() : e;


log.info("error while deleting");
}
}
}

};