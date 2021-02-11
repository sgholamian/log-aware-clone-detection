//,temp,SortedRanges.java,134,177,temp,SortedRanges.java,80,125
//,3
public class xxx {
  synchronized void remove(Range range) {
    if(range.isEmpty()) {
      return;
    }
    long startIndex = range.getStartIndex();
    long endIndex = range.getEndIndex();
    //make sure that there are no overlapping ranges
    SortedSet<Range> headSet = ranges.headSet(range);
    if(headSet.size()>0) {
      Range previousRange = headSet.last();
      LOG.debug("previousRange "+previousRange);
      if(startIndex<previousRange.getEndIndex()) {
        //previousRange overlaps this range
        //narrow down the previousRange
        if(ranges.remove(previousRange)) {
          indicesCount-=previousRange.getLength();
          LOG.debug("removed previousRange "+previousRange);
        }
        add(previousRange.getStartIndex(), startIndex);
        if(endIndex<=previousRange.getEndIndex()) {
          add(endIndex, previousRange.getEndIndex());
        }
      }
    }
    
    Iterator<Range> tailSetIt = ranges.tailSet(range).iterator();
    while(tailSetIt.hasNext()) {
      Range nextRange = tailSetIt.next();
      LOG.debug("nextRange "+nextRange +"   startIndex:"+startIndex+
          "  endIndex:"+endIndex);
      if(endIndex>nextRange.getStartIndex()) {
        //nextRange overlaps this range
        //narrow down the nextRange
        tailSetIt.remove();
        indicesCount-=nextRange.getLength();
        if(endIndex<nextRange.getEndIndex()) {
          add(endIndex, nextRange.getEndIndex());
          break;
        }
      } else {
        break;
      }
    }
  }

};