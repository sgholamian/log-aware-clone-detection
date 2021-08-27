//,temp,GenericUDAFSum.java,555,570,temp,GenericUDAFSum.java,277,296
//,3
public class xxx {
    @Override
    public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
      assert (parameters.length == 1);
      try {
        if (isEligibleValue((SumLongAgg) agg, parameters[0])) {
          ((SumLongAgg)agg).empty = false;
          ((SumLongAgg)agg).sum += PrimitiveObjectInspectorUtils.getLong(parameters[0], inputOI);
        }
      } catch (NumberFormatException e) {
        if (!warned) {
          warned = true;
          LOG.warn(getClass().getSimpleName() + " "
              + StringUtils.stringifyException(e));
        }
      }
    }

};