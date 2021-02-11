//,temp,sample_5284.java,2,16,temp,sample_2023.java,2,16
//,3
public class xxx {
public void dummy_method(){
generateRows(numRows, ht, family, qf, value);
Scan scan = new Scan();
scan.setMaxVersions();
List<RowRange> ranges = new ArrayList<>();
ranges.add(new RowRange(Bytes.toBytes(10), true, Bytes.toBytes(20), false));
ranges.add(new RowRange(Bytes.toBytes(20), false, Bytes.toBytes(40), false));
ranges.add(new RowRange(Bytes.toBytes(65), true, Bytes.toBytes(75), false));
MultiRowRangeFilter filter = new MultiRowRangeFilter(ranges);
scan.setFilter(filter);
int resultsSize = getResultsSize(ht, scan);


log.info("found results");
}

};