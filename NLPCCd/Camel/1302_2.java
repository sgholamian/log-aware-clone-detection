//,temp,sample_3298.java,2,17,temp,sample_3297.java,2,17
//,3
public class xxx {
public void dummy_method(){
Collections.sort(models, new OtherComparator());
File file = new File(userGuideDir, "SUMMARY.md");
StringBuilder other = new StringBuilder();
other.append("* Miscellaneous Components\n");
for (OtherModel model : models) {
String line = "\t* " + link(model) + "\n";
other.append(line);
}
boolean updated = updateOthers(file, other.toString());
if (updated) {


log.info("updated user guide file");
}
}

};