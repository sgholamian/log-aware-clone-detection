//,temp,sample_657.java,2,16,temp,sample_656.java,2,16
//,2
public class xxx {
public void dummy_method(){
Integer c = 0;
for (final String entry : vmVifs) {
final String[] parts = entry.split(",");
final String[] ippart = parts[1].split("=");
assert ippart.length == 2 : "Invalid entry: " + entry;
if ("mac".equals(ippart[0]) && ippart[1].equals(ip)) {
return c;
}
c += 1;
}


log.info("no vif matched ip in");
}

};