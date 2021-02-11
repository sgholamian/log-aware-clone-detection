//,temp,sample_2085.java,2,17,temp,sample_1018.java,2,17
//,3
public class xxx {
public void dummy_method(){
int count = request.getCount();
long cachedOffset = nextOffset.get();
int originalCount = WriteCtx.INVALID_ORIGINAL_COUNT;
if (LOG.isDebugEnabled()) {
LOG.debug("requested offset=" + offset + " and current offset=" + cachedOffset);
}
if (offset + count <= cachedOffset) {
return null;
}
if ((offset < cachedOffset) && (offset + count > cachedOffset)) {


log.info("got overwrite with appended data d d current offset d drop the overlapped section d d and append new data d d");
}
}

};