package epicsquid.gadgetry.core.lib.predicates;

import java.util.function.Predicate;

public class PredicateTrue<T> implements Predicate<T> {

  @Override
  public boolean test(T arg0) {
    return true;
  }

}
