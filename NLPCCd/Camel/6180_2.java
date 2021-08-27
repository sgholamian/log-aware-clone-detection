//,temp,sample_5750.java,2,19,temp,sample_5751.java,2,18
//,3
public class xxx {
public void dummy_method(){
final String name = asName(file);
final ErrorDetail detail = new ErrorDetail();
validate(file, detail);
if (detail.hasErrors()) {
failed = true;
if (detail.isMissingDescription()) {
}
if (detail.isMissingLabel()) {
}
if (detail.isMissingSyntax()) {


log.info("missing syntax on endpoint");
}
}
}

};