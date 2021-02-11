//,temp,SortedRanges.java,134,177,temp,SortedRanges.java,80,125
//,3
public class xxx {
  synchronized void add(Range range){
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
        //remove the previousRange
        if(ranges.remove(previousRange)) {
          indicesCount-=previousRange.getLength();
        }
        //expand this range
        startIndex = previousRange.getStartIndex();
        endIndex = endIndex>=previousRange.getEndIndex() ?
                          endIndex : previousRange.getEndIndex();
      }
    }
    
    Iterator<Range> tailSetIt = ranges.tailSet(range).iterator();
    while(tailSetIt.hasNext()) {
      Range nextRange = tailSetIt.next();
      LOG.debug("nextRange "+nextRange +"   startIndex:"+startIndex+
          "  endIndex:"+endIndex);
      if(endIndex>=nextRange.getStartIndex()) {
        //nextRange overlaps this range
        //remove the nextRange
        tailSetIt.remove();
        indicesCount-=nextRange.getLength();
        if(endIndex<nextRange.getEndIndex()) {
          //expand this range
          endIndex = nextRange.getEndIndex();
          break;
        }
      } else {
        break;
      }
    }
    add(startIndex,endIndex);
  }

};