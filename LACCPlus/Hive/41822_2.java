//,temp,VectorReduceSinkCommonOperator.java,350,374,temp,MapOperator.java,582,597
//,3
public class xxx {
  protected final void rowsForwarded(int childrenDone, int rows) {
    numRows += rows;
    if (LOG.isInfoEnabled()) {
      while (numRows >= cntr) {
        cntr = logEveryNRows == 0 ? cntr * 10 : numRows + logEveryNRows;
        if (cntr < 0 || numRows < 0) {
          cntr = 1;
          numRows = 0;
        }
        LOG.info(toString() + ": records read - " + numRows);
      }
    }
    if (childrenDone == currentCtxs.length) {
      setDone(true);
    }
  }

};