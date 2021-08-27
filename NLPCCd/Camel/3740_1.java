//,temp,sample_3301.java,2,17,temp,sample_3302.java,2,17
//,3
public class xxx {
public void dummy_method(){
Collections.sort(models, new LanguageComparator());
File file = new File(userGuideDir, "SUMMARY.md");
StringBuilder languages = new StringBuilder();
languages.append("* Expression Languages\n");
for (LanguageModel model : models) {
String line = "\t* " + link(model) + "\n";
languages.append(line);
}
boolean updated = updateLanguages(file, languages.toString());
if (updated) {


log.info("updated user guide file");
}
}

};