//,temp,sample_2106.java,2,16,temp,sample_2107.java,2,17
//,3
public class xxx {
private void scanForFinalFiles(WatchedPathInfo watchedPathInfo, Path path) {
try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path) ) {
for (Path p : dirStream) {
if (p.getFileName().toString().equals(ShuffleHandler.DATA_FILE_NAME)) {
registerFoundAttempt(watchedPathInfo.pathIdentifier, null, path);
} else if (p.getFileName().toString().equals(ShuffleHandler.INDEX_FILE_NAME)) {
registerFoundAttempt(watchedPathInfo.pathIdentifier, path, null);
} else {


log.info("ignoring unknown file");
}
}
}
}

};