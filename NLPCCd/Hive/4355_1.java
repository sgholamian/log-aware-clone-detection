//,temp,sample_1995.java,2,16,temp,sample_1994.java,2,13
//,3
public class xxx {
public FilterCompat.Filter setFilter(final JobConf conf, MessageType schema) {
SearchArgument sarg = ConvertAstToSearchArg.createFromConf(conf);
if (sarg == null) {
return null;
}
FilterPredicate p = ParquetFilterPredicateConverter.toFilterPredicate(sarg, schema);
if (p != null) {
ParquetInputFormat.setFilterPredicate(conf, p);
return FilterCompat.get(p);
} else {


log.info("no parquet predicate push down is generated");
}
}

};