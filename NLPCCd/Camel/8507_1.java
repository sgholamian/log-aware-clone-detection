//,temp,sample_593.java,2,17,temp,sample_589.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (deprecated) {
docTitle += " (deprecated)";
}
boolean exists = file.exists();
boolean updated;
updated = updateTitles(file, docTitle);
updated |= updateAvailableFrom(file, model.getFirstVersion());
String options = templateLanguageOptions(model);
updated |= updateLanguageOptions(file, options);
if (updated) {


log.info("updated doc file");
}
}

};