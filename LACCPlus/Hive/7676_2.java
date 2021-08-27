//,temp,GenericUDAFSum.java,555,570,temp,GenericUDAFSum.java,277,296
//,3
public class xxx {
    @Override
    public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
      assert (parameters.length == 1);
      try {
        if (isEligibleValue((SumHiveDecimalWritableAgg) agg, parameters[0])) {
          ((SumHiveDecimalWritableAgg)agg).empty = false;
          ((SumHiveDecimalWritableAgg)agg).sum.mutateAdd(
              PrimitiveObjectInspectorUtils.getHiveDecimal(parameters[0], inputOI));
        }
      } catch (NumberFormatException e) {
        if (!warned) {
          warned = true;
          LOG.warn(getClass().getSimpleName() + " "
              + StringUtils.stringifyException(e));
          LOG
          .warn(getClass().getSimpleName()
              + " ignoring similar exceptions.");
        }
      }
    }

};