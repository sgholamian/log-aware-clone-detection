//,temp,sample_3300.java,2,17,temp,sample_3299.java,2,17
//,3
public class xxx {
public void dummy_method(){
File file = new File(userGuideDir, "SUMMARY.md");
StringBuilder dataFormats = new StringBuilder();
dataFormats.append("* Data Formats\n");
for (DataFormatModel model : models) {
String line = "\t* " + link(model) + "\n";
dataFormats.append(line);
}
boolean updated = updateDataFormats(file, dataFormats.toString());
if (updated) {
} else {


log.info("no changes to user guide file");
}
}

};