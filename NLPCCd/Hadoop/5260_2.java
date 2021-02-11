//,temp,sample_3504.java,2,12,temp,sample_9071.java,2,10
//,3
public class xxx {
public void deleteAsUser(DeletionAsUserContext ctx) throws IOException, InterruptedException {
Path subDir = ctx.getSubDir();
List<Path> baseDirs = ctx.getBasedirs();
if (baseDirs == null || baseDirs.size() == 0) {


log.info("deleting absolute path");
}
}

};