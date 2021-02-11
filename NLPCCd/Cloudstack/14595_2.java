//,temp,sample_657.java,2,16,temp,sample_656.java,2,16
//,2
public class xxx {
public void dummy_method(){
Integer c = 0;
for (final String entry : vmVifs) {
final String[] parts = entry.split(",");
final String[] macpart = parts[0].split("=");
assert macpart.length == 2 : "Invalid entry: " + entry;
if ("mac".equals(macpart[0]) && macpart[1].equals(mac)) {
return c;
}
c += 1;
}


log.info("no vif matched mac in");
}

};