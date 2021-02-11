//,temp,sample_6344.java,2,9,temp,sample_1608.java,2,11
//,3
public class xxx {
private boolean removeResourceFromCacheFileSystem(Path path) throws IOException {
Path renamedPath = new Path(path.toString() + RENAMED_SUFFIX);
if (fs.rename(path, renamedPath)) {
return fs.delete(renamedPath, true);
} else {


log.info("we were not able to rename the directory to we will leave it intact");
}
}

};