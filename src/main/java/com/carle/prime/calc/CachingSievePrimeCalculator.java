package com.carle.prime.calc;

import static java.util.Arrays.asList;

import java.util.BitSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

/**
 * Overrides the standard SievePrimeCalculator implementation to provide caching. The
 * maximum upper bound and the corresponding set of prime numbers are cached. If the
 * upperBound is less than or equal to the max upper bound (i.e. the highest upper bound
 * value received on any previous request), then we can return the corresponding prime
 * numbers straight away (using TreeSet.headSet() to return only the primes up to the
 * upper bound for this request). Otherwise, we recalculate the primes.
 * <p>
 * TODO: Refactor to separate the caching strategy from the calculator implementation.
 * This will also facilitate testing, i.e. mock the caching strategy, and then verify in
 * the test that we had a cache hit or a cache miss.
 * <p>
 * TODO: The current implementation will not prevent the case where a request is received
 * that triggers a new calculation, but there is already a calculation in progress for an
 * upper bound value greater than or equal to the current request. In this scenario, we
 * want the second request to block until the first request has completed, then both
 * requests can return their result based on the new cached value. A 'memoizer' can be
 * used to deal with this situation (e.g. Guava's Suppliers.memoize()).
 *
 * @author Matt Carle
 *
 */
@Component("cachingSievePrimeCalculator")
public class CachingSievePrimeCalculator extends SievePrimeCalculator {
  private int maxUpperBound;
  private NavigableSet<Integer> primes;

  // initial state is that 2 is the only known prime
  public CachingSievePrimeCalculator() {
    maxUpperBound = 2;
    primes = new TreeSet<>(asList(2));
  }

  @Override
  public Set<Integer> findPrimes(final int upperBound) {
    final Set<Integer> result;
    if (upperBound <= maxUpperBound) {
      // no calculation required - just return the required subset of the cached primes
      final Set<Integer> primesUpToBound;
      synchronized (this) {
        primesUpToBound = primes.headSet(upperBound, true);
      }
      result = primesUpToBound;

    } else {
      // recalculate the primes
      NavigableSet<Integer> primesNew = new TreeSet<>();
      findPrimes(upperBound, primesNew);

      // update the cached primes and max upper bound atomically
      synchronized (this) {
        maxUpperBound = upperBound;
        primes = primesNew;
      }

      result = primes;
    }

    return result;
  }
}
