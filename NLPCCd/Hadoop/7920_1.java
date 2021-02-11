//,temp,sample_4233.java,2,13,temp,sample_137.java,2,17
//,3
public class xxx {
public Configuration bindArgs(Configuration config, List<String> args) throws Exception {
Configuration configuration = super.bindArgs(config, args);
if (!args.isEmpty()) {
StringBuilder argsList = new StringBuilder();
for (String arg : args) {
argsList.append('"').append(arg).append("\" ");
}


log.info("got arguments");
}
}

};