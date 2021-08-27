//,temp,sample_2102.java,2,17,temp,sample_2101.java,2,19
//,3
public class xxx {
public void dummy_method(){
try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(resolvedPath)) {
for (Path path : dirStream) {
if (path.toFile().isDirectory()) {
watchedPathInfo = new WatchedPathInfo(parentWatchedPathInfo, Type.FINAL, path.getFileName().toString());
registerDir(path, watchedPathInfo);
scanForFinalFiles(watchedPathInfo, path);
} else {
}
}
} catch (IOException e) {


log.info("unable to list files under");
}
}

};