//,temp,sample_5711.java,2,12,temp,sample_4239.java,2,17
//,3
public class xxx {
public void testAggregationWithReturnValue() throws Throwable {
Table table = util.getConnection().getTable(TEST_TABLE);
Map<byte[], SumResponse> results = sum(table, TEST_FAMILY, TEST_QUALIFIER, ROWS[0], ROWS[ROWS.length - 1]);
int sumResult = 0;
int expectedResult = 0;
for (Map.Entry<byte[], SumResponse> e : results.entrySet()) {


log.info("got value for region");
}
}

};