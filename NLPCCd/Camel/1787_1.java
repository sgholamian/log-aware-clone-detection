//,temp,sample_293.java,2,10,temp,sample_3141.java,2,10
//,2
public class xxx {
public static boolean assertPredicate(final Predicate predicate, Exchange exchange, boolean expected) {
if (expected) {
PredicateAssertHelper.assertMatches(predicate, "Predicate failed: ", exchange);
}
boolean value = predicate.matches(exchange);


log.info("evaluated predicate on exchange result");
}

};