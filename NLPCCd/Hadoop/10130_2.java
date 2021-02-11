//,temp,sample_8893.java,2,16,temp,sample_8894.java,2,16
//,3
public class xxx {
public void dummy_method(){
headIndex = 0;
dd.moveBlockToHead(temp, curIndex, headIndex);
assertEquals( "Moving head to the head of the list shopuld not change the list", temp, dd.getBlockListHeadForTesting());
temp = dd.getBlockListHeadForTesting();
assertNotNull("Head should not be null", temp);
int c = MAX_BLOCKS - 1;
while (temp != null) {
assertEquals("Expected element is not on the list", blockInfoList.get(c--), temp);
temp = temp.getNext(0);
}


log.info("moving random blocks to the head of the list");
}

};