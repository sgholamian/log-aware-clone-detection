//,temp,sample_5589.java,2,18,temp,sample_5590.java,2,16
//,3
public class xxx {
public void dummy_method(){
currentWriter.append(cell);
lastCell = cell;
++cellsInCurrentWriter;
cellsSeen = cellsInCurrentWriter;
if (this.sourceScanner != null) {
cellsSeen = Math.max(cellsSeen, this.sourceScanner.getEstimatedNumberOfKvsScanned() - cellsSeenInPrevious);
}
if (lastRowInCurrentWriter == null && existingWriters.size() < targetCount && cellsSeen >= targetCells) {
lastRowInCurrentWriter = CellUtil.cloneRow(cell);
if (LOG.isDebugEnabled()) {


log.info("preparing to start a new writer after row observed kvs and wrote out kvs");
}
}
}

};