//,temp,sample_8893.java,2,16,temp,sample_8894.java,2,16
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < MAX_BLOCKS; i++) {
curIndex = blockInfoList.get(i).findStorageInfo(dd);
headIndex = dd.moveBlockToHead(blockInfoList.get(i), curIndex, headIndex);
assertEquals("Block should be at the head of the list now.", blockInfoList.get(i), dd.getBlockListHeadForTesting());
}
BlockInfo temp = dd.getBlockListHeadForTesting();
curIndex = 0;
headIndex = 0;
dd.moveBlockToHead(temp, curIndex, headIndex);
assertEquals( "Moving head to the head of the list shopuld not change the list", temp, dd.getBlockListHeadForTesting());


log.info("checking elements of the list");
}

};