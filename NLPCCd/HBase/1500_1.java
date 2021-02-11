//,temp,sample_2097.java,2,16,temp,sample_2098.java,2,16
//,3
public class xxx {
public Optional<Cell> getLastKey() {
if (top) {
return super.getLastKey();
}
HFileScanner scanner = getScanner(true, true);
try {
if (scanner.seekBefore(this.splitCell)) {
return Optional.ofNullable(scanner.getKey());
}
} catch (IOException e) {


log.info("failed seekbefore");
}
}

};