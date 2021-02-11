//,temp,sample_9357.java,2,17,temp,sample_9356.java,2,17
//,3
public class xxx {
public void dummy_method(){
while (numExpectedXAttrs < numGeneratedXAttrs) {
final int numToAdd = rand.nextInt(5)+1;
List<XAttr> toAdd = Lists.newArrayListWithCapacity(numToAdd);
for (int i = 0; i < numToAdd; i++) {
if (numExpectedXAttrs >= numGeneratedXAttrs) {
break;
}
toAdd.add(generatedXAttrs.get(numExpectedXAttrs));
numExpectedXAttrs++;
}


log.info("attempting to add xattrs");
}
}

};