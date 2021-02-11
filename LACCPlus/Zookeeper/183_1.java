//,temp,LogSkipList.java,65,70,temp,ZooTrace.java,69,73
//,3
public class xxx {
    public void addMark(long time, long bytes, long skipped) {
	if (LOG.isTraceEnabled()) {
	    LOG.trace("addMark (time:" + time + ", bytes: " + bytes + ", skipped: " + skipped + ")");
	}
	marks.add(new Mark(time, bytes, skipped));
    }

};