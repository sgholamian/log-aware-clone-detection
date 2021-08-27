//,temp,sample_1788.java,2,14,temp,sample_1789.java,2,15
//,2
public class xxx {
private void incrCounter(String line) {
String  trimmedLine = line.substring(counterPrefix.length()).trim();
String[] columns = trimmedLine.split(",");
if (columns.length == 3) {
try {
reporter.incrCounter(columns[0], columns[1], Long.parseLong(columns[2]));
} catch (NumberFormatException e) {


log.info("cannot parse counter increment from line");
}
}
}

};