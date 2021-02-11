//,temp,sample_2097.java,2,16,temp,sample_2098.java,2,16
//,3
public class xxx {
public Optional<Cell> getFirstKey() {
if (!firstKeySeeked) {
HFileScanner scanner = getScanner(true, true, false);
try {
if (scanner.seekTo()) {
this.firstKey = Optional.ofNullable(scanner.getKey());
}
firstKeySeeked = true;
} catch (IOException e) {


log.info("failed seekto first kv in the file");
}
}
}

};