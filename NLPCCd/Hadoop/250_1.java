//,temp,sample_1956.java,2,9,temp,sample_1957.java,2,9
//,3
public class xxx {
public void createCheckpoint(Date date) throws IOException {
Collection<FileStatus> trashRoots = fs.getTrashRoots(false);
for (FileStatus trashRoot: trashRoots) {


log.info("trashpolicydefault createcheckpoint for trashroot");
}
}

};