//,temp,DemuxOperator.java,119,192,temp,ExecReducer.java,87,142
//,3
public class xxx {
  @Override
  protected void initializeOp(Configuration hconf) throws HiveException {
    super.initializeOp(hconf);
    // A DemuxOperator should have at least one child
    if (childOperatorsArray.length == 0) {
      throw new HiveException(
          "Expected number of children is at least 1. Found : " + childOperatorsArray.length);
    }

    newTagToOldTag = toArray(conf.getNewTagToOldTag());
    newTagToChildIndex = toArray(conf.getNewTagToChildIndex());
    childInputObjInspectors = new ObjectInspector[childOperators.size()][];
    cntrs = new long[newTagToOldTag.length];
    nextCntrs = new long[newTagToOldTag.length];

    try {
      // We populate inputInspectors for all children of this DemuxOperator.
      // Those inputObjectInspectors are stored in childInputObjInspectors.
      for (int i = 0; i < newTagToOldTag.length; i++) {
        int newTag = i;
        int oldTag = newTagToOldTag[i];
        int childIndex = newTagToChildIndex[newTag];
        cntrs[newTag] = 0;
        nextCntrs[newTag] = 0;
        TableDesc keyTableDesc = conf.getKeysSerializeInfos().get(newTag);
        AbstractSerDe inputKeyDeserializer = ReflectionUtil.newInstance(keyTableDesc.getSerDeClass(), null);
        inputKeyDeserializer.initialize(null, keyTableDesc.getProperties(), null);

        TableDesc valueTableDesc = conf.getValuesSerializeInfos().get(newTag);
        AbstractSerDe inputValueDeserializer = ReflectionUtil.newInstance(valueTableDesc
            .getSerDeClass(), null);
        inputValueDeserializer.initialize(null, valueTableDesc.getProperties(), null);

        List<ObjectInspector> oi = new ArrayList<ObjectInspector>();
        oi.add(inputKeyDeserializer.getObjectInspector());
        oi.add(inputValueDeserializer.getObjectInspector());
        int childParentsCount = conf.getChildIndexToOriginalNumParents().get(childIndex);
        // Multiple newTags can point to the same child (e.g. when the child is a JoinOperator).
        // So, we first check if childInputObjInspectors contains the key of childIndex.
        if (childInputObjInspectors[childIndex] == null) {
          childInputObjInspectors[childIndex] = new ObjectInspector[childParentsCount];
        }
        ObjectInspector[] ois = childInputObjInspectors[childIndex];
        ois[oldTag] = ObjectInspectorFactory
            .getStandardStructObjectInspector(Utilities.reduceFieldNameList, oi);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    childrenDone = 0;
    newChildOperatorsTag = new int[childOperators.size()][];
    for (int i = 0; i < childOperators.size(); i++) {
      Operator<? extends OperatorDesc> child = childOperators.get(i);
      List<Integer> childOperatorTags = new ArrayList<Integer>();
      if (child instanceof MuxOperator) {
        // This DemuxOperator can appear multiple times in MuxOperator's
        // parentOperators
        int index = 0;
        for (Operator<? extends OperatorDesc> parent: child.getParentOperators()) {
          if (this == parent) {
            childOperatorTags.add(index);
          }
          index++;
        }
      } else {
        childOperatorTags.add(child.getParentOperators().indexOf(this));
      }
      newChildOperatorsTag[i] = toArray(childOperatorTags);
    }
    if (LOG.isInfoEnabled()) {
      LOG.info("newChildOperatorsTag " + Arrays.toString(newChildOperatorsTag));
    }

  }

};