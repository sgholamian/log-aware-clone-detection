//,temp,sample_4307.java,2,17,temp,sample_1653.java,2,18
//,3
public class xxx {
public void dummy_method(){
op.close();
if (conf.getBoolean(FORCE_COMBINER_CONF, true)) {
conf.setInt("mapreduce.map.combine.minspills", 1);
}
List<String> argv = new ArrayList<>(Arrays.asList(args));
argv.add(inputPath.toString());
Tool tool = new ImportTsv();
assertEquals(0, ToolRunner.run(conf, tool, argv.toArray(args)));
validateTable(conf, TableName.valueOf(table), family, valueMultiplier, dataAvailable);
if (conf.getBoolean(DELETE_AFTER_LOAD_CONF, true)) {


log.info("deleting test subdirectory");
}
}

};