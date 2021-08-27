//,temp,sample_2432.java,2,6,temp,sample_4149.java,2,8
//,3
public class xxx {
private RecordReader<NullWritable, VectorizedRowBatch> wrapLlapReader( List<Integer> includedCols, LlapRecordReader rr, InputSplit split) throws IOException {
if (sourceInputFormat instanceof BatchToRowInputFormat) {


log.info("using batch to row converter for split");
}
}

};