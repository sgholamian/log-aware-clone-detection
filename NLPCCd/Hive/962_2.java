//,temp,sample_5382.java,2,16,temp,sample_4266.java,2,17
//,3
public class xxx {
public Object deserialize(Writable blob) throws SerDeException {
if (inputPattern == null) {
throw new SerDeException( "This table does not have serde property \"input.regex\"!");
}
Text rowText = (Text) blob;
Matcher m = inputPattern.matcher(rowText.toString());
if (!m.matches()) {
unmatchedRows++;
if (unmatchedRows >= nextUnmatchedRows) {
nextUnmatchedRows = getNextNumberToDisplay(nextUnmatchedRows);


log.info("unmatched rows are found");
}
}
}

};