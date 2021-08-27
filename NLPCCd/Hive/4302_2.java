//,temp,sample_1130.java,2,12,temp,sample_2283.java,2,14
//,3
public class xxx {
public DecomposedPredicate decomposePredicate(JobConf conf, Deserializer deserializer, ExprNodeDesc desc) {
if (!(deserializer instanceof AccumuloSerDe)) {
throw new RuntimeException("Expected an AccumuloSerDe but got " + deserializer.getClass().getName());
}
AccumuloSerDe serDe = (AccumuloSerDe) deserializer;
if (serDe.getIteratorPushdown()) {
return predicateHandler.decompose(conf, desc);
} else {


log.info("set to ignore accumulo iterator pushdown skipping predicate handler");
}
}

};