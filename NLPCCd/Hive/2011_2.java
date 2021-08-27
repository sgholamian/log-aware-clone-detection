//,temp,sample_584.java,2,11,temp,sample_582.java,2,11
//,2
public class xxx {
private int dropMacro(DropMacroDesc dropMacroDesc) {
try {
FunctionRegistry.unregisterTemporaryUDF(dropMacroDesc.getMacroName());
return 0;
} catch (HiveException e) {


log.info("drop macro");
}
}

};