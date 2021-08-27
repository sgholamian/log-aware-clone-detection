//,temp,sample_584.java,2,11,temp,sample_582.java,2,11
//,2
public class xxx {
private int dropTemporaryFunction(DropFunctionDesc dropFunctionDesc) {
try {
FunctionRegistry.unregisterTemporaryUDF(dropFunctionDesc.getFunctionName());
return 0;
} catch (HiveException e) {


log.info("drop function");
}
}

};