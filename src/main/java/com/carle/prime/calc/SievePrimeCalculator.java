package com.carle.prime.calc;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.BitSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

/**
 * Calculate prime numbers using the Sieve of Eratosthenes algorithm.
 *
 * @see http://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
 * @author Matt Carle
 */
@Component("sievePrimeCalculator")
public class SievePrimeCalculator implements PrimeCalculator {
  private static final Logger logger = LoggerFactory.getLogger(SievePrimeCalculator.class);

  @Override
  public Set<Integer> findPrimes(final int upperBound) {
    final Set<Integer> results = new LinkedHashSet<>(max(upperBound / 2, 0));

    findPrimes(upperBound, results);

    return results;
  }

  protected Set<Integer> findPrimes(final int upperBound, final Set<Integer> results) {
    Stopwatch stopwatch = Stopwatch.createStarted();

    // use linked hash set to ensure primes appear in order
    if (upperBound > 1) {
      // use a BitSet to minimise memory usage
      final BitSet isComposite = new BitSet(upperBound + 1);

      // mark all the numbers that are composite, i.e. not prime
      markComposites(isComposite, upperBound);

      // then traverse the BitSet, adding any that are not composite to the results
      filterPrimes(isComposite, upperBound, results);
    }

    logger.info(format("findPrimes(%,d): Total elapsed time=%,dms", upperBound, stopwatch.elapsed(MILLISECONDS)));

    return results;
  }

  protected void markComposites(final BitSet isComposite, final int upperBound) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    long iterationsOuter = 0L, iterationsInner = 0L;
    final int sqrtOfUpperBound = (int) sqrt(upperBound);

    // there are no even primes > 2, so consider odd numbers only, starting from 3
    for (int i = 3; i <= sqrtOfUpperBound; i += 2) {
      if (!isComposite.get(i)) {
        for (int j = i * i; j <= upperBound; j += i) {
          isComposite.set(j);
          iterationsInner++;
        }
      }
      iterationsOuter++;
    }

    logger.info(format("findPrimes(%,d): Total markComposites iterations: outer=%,d, inner=%,d, total=%,d. Elapsed=%,dms",
        upperBound, iterationsOuter, iterationsInner, (iterationsOuter + iterationsInner),
        stopwatch.elapsed(MILLISECONDS)));
  }

  protected Set<Integer> filterPrimes(final BitSet isComposite, final int upperBound,
      final Set<Integer> results) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    long iterations = 0L;

    // add 2 explicitly, then check each odd number > 2 to see if it's a prime (and if so, add it)
    results.add(2);
    for (int i = 3; i <= upperBound; i += 2) {
      if (!isComposite.get(i)) {
        results.add(i);
      }
      iterations++;
    }

    logger.info(format("findPrimes(%,d): Total filterPrimes iterations: %,d. Elapsed time=%,dms", upperBound,
        iterations, stopwatch.elapsed(MILLISECONDS)));

    return results;
  }
}
