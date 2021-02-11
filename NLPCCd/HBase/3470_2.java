//,temp,sample_5284.java,2,16,temp,sample_2023.java,2,16
//,3
public class xxx {
public void dummy_method(){
MultiRowRangeFilter filter1 = new MultiRowRangeFilter(ranges1);
List<RowRange> ranges2 = new ArrayList<>();
ranges2.add(new RowRange(Bytes.toBytes(15), true, Bytes.toBytes(20), false));
ranges2.add(new RowRange(Bytes.toBytes(9985), true, Bytes.toBytes(9990), false));
MultiRowRangeFilter filter2 = new MultiRowRangeFilter(ranges2);
FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
filterList.addFilter(filter1);
filterList.addFilter(filter2);
scan.setFilter(filterList);
int resultsSize = getResultsSize(ht, scan);


log.info("found results");
}

};