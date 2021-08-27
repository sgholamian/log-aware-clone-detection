//,temp,sample_4776.java,2,18,temp,sample_4774.java,2,18
//,3
public class xxx {
public void dummy_method(){
assert (parameters.length == 1);
try {
if (isEligibleValue((SumDoubleAgg) agg, parameters[0])) {
((SumDoubleAgg)agg).empty = false;
((SumDoubleAgg)agg).sum += PrimitiveObjectInspectorUtils.getDouble(parameters[0], inputOI);
}
} catch (NumberFormatException e) {
if (!warned) {
warned = true;
LOG.warn(getClass().getSimpleName() + " " + StringUtils.stringifyException(e));


log.info("ignoring similar exceptions");
}
}
}

};