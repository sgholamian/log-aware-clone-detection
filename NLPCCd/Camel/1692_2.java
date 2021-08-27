//,temp,sample_7903.java,2,18,temp,sample_7902.java,2,16
//,3
public class xxx {
public void processResult(int rc, String node, Object ctx, Stat statistics) {
if (Code.NONODE.equals(Code.get(rc))) {
if (configuration.isCreate()) {
ProductionContext context = (ProductionContext)ctx;
OperationResult<String> result = null;
try {
result = createNode(context);
} catch (Exception e) {


log.info("error trying to create node s");
}
}
}
}

};