//,temp,DemuxOperator.java,119,192,temp,ExecReducer.java,87,142
//,3
public class xxx {
  @Override
  public void configure(JobConf job) {
    rowObjectInspector = new ObjectInspector[Byte.MAX_VALUE];
    ObjectInspector[] valueObjectInspector = new ObjectInspector[Byte.MAX_VALUE];
    ObjectInspector keyObjectInspector;

    Utilities.tryLoggingClassPaths(job, LOG);
    jc = job;

    ReduceWork gWork = Utilities.getReduceWork(job);

    reducer = gWork.getReducer();
    reducer.setParentOperators(null); // clear out any parents as reducer is the
    // root
    isTagged = gWork.getNeedsTagging();
    try {
      keyTableDesc = gWork.getKeyDesc();
      inputKeySerDe = ReflectionUtils.newInstance(keyTableDesc
          .getSerDeClass(), null);
      inputKeySerDe.initialize(null, keyTableDesc.getProperties(), null);
      keyObjectInspector = inputKeySerDe.getObjectInspector();
      valueTableDesc = new TableDesc[gWork.getTagToValueDesc().size()];
      for (int tag = 0; tag < gWork.getTagToValueDesc().size(); tag++) {
        // We should initialize the SerDe with the TypeInfo when available.
        valueTableDesc[tag] = gWork.getTagToValueDesc().get(tag);
        AbstractSerDe valueObjectSerDe = ReflectionUtils.newInstance(valueTableDesc[tag].getSerDeClass(), null);
        valueObjectSerDe.initialize(null, valueTableDesc[tag].getProperties(), null);
        inputValueDeserializer[tag] = valueObjectSerDe;
        valueObjectInspector[tag] = inputValueDeserializer[tag].getObjectInspector();

        ArrayList<ObjectInspector> ois = new ArrayList<ObjectInspector>();
        ois.add(keyObjectInspector);
        ois.add(valueObjectInspector[tag]);
        rowObjectInspector[tag] = ObjectInspectorFactory
            .getStandardStructObjectInspector(Utilities.reduceFieldNameList, ois);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    MapredContext.init(false, new JobConf(jc));

    // initialize reduce operator tree
    try {
      LOG.info(reducer.dump(0));
      reducer.initialize(jc, rowObjectInspector);
    } catch (Throwable e) {
      abort = true;
      if (e instanceof OutOfMemoryError) {
        // Don't create a new object if we are already out of memory
        throw (OutOfMemoryError) e;
      } else {
        throw new RuntimeException("Reduce operator initialization failed", e);
      }
    }
  }

};