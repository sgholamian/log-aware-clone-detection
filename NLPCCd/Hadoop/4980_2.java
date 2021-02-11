//,temp,sample_5416.java,2,15,temp,sample_404.java,2,17
//,3
public class xxx {
public void dummy_method(){
int i = 0;
Iterator<Segment<K,V>> itr = segmentList.iterator();
while (itr.hasNext()) {
Segment<K,V> s = itr.next();
if (i == readSegmentIndex) {
break;
}
s.close();
itr.remove();
i++;


log.info("dropping a segment");
}
}

};