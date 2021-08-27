//,temp,sample_134.java,2,17,temp,sample_135.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (isRecursive) {
pathToReload = folderKeys.get(key);
} else {
pathToReload = folder;
}
for (WatchEvent<?> event : key.pollEvents()) {
WatchEvent<Path> we = (WatchEvent<Path>) event;
Path path = we.context();
String name = pathToReload.resolve(path).toAbsolutePath().toFile().getAbsolutePath();
if (name.toLowerCase(Locale.US).endsWith(".xml")) {


log.info("modified created xml file");
}
}
}

};