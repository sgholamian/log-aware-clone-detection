//,temp,sample_2414.java,2,15,temp,sample_2411.java,2,13
//,3
public class xxx {
protected boolean accept(Path p, @CheckForNull Boolean isDir) {
if (!isValidName(p.getName())) {
return false;
}
try {
return isDirectory(fs, isDir, p);
} catch (IOException e) {


log.info("an error occurred while verifying if is a valid directory returning not valid and continuing");
}
}

};