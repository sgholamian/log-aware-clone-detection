//,temp,sample_4766.java,2,11,temp,sample_4765.java,2,11
//,2
public class xxx {
public List<HiveInputSplit> filter(HiveInputSplit[] splits) throws IOException {
long sumSplitLengths = 0;
List<HiveInputSplit> newSplits = new ArrayList<>();
Arrays.sort(splits, new HiveInputSplitComparator());
for (HiveInputSplit split : splits) {


log.info("split end");
}
}

};