//,temp,sample_1878.java,2,17,temp,sample_8413.java,2,17
//,3
public class xxx {
public void dummy_method(){
String code = sourceToString(source);
code = header + code;
if (target.exists()) {
String existing = FileUtils.readFileToString(target);
if (!code.equals(existing)) {
FileUtils.write(target, code, false);
} else {
}
} else {
FileUtils.write(target, code);


log.info("created file");
}
}

};