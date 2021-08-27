//,temp,sample_7531.java,2,10,temp,sample_292.java,2,10
//,2
public class xxx {
public static void assertPredicateDoesNotMatch(Predicate predicate, Exchange exchange) {
try {
PredicateAssertHelper.assertMatches(predicate, "Predicate should match: ", exchange);
} catch (AssertionError e) {


log.info("caught expected assertion error");
}
}

};