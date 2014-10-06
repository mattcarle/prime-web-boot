package com.carle.prime.calc;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.google.common.collect.ImmutableSortedSet;

/**
 * Unit tests for the PrimeCalculator
 *
 */
@RunWith(Parameterized.class)
public class PrimeCalculatorTest {
  private final PrimeCalculator primeCalc;

  public PrimeCalculatorTest(final PrimeCalculator primeCalc) {
    this.primeCalc = primeCalc;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> calculators() {
    return asList(new Object[][] {{new SievePrimeCalculator()}, {new CachingSievePrimeCalculator()}});
  }

  @Test
  public void testFindPrimesUpTo0() {
    runCompareResultsTest(primeCalc, 0, emptySet());
  }

  @Test
  public void testFindPrimesUpTo1() {
    runCompareResultsTest(primeCalc, 1, emptySet());
  }

  @Test
  public void testFindPrimesUpTo2() {
    runCompareResultsTest(primeCalc, 2, ImmutableSortedSet.copyOf(asList(2)));
  }

  // no negative primes
  @Test
  public void testFindPrimesForNegativeUpperBound() {
    runCompareResultsTest(primeCalc, -10, emptySet());
  }

  // edge case test: the upper bound in this test (i.e. 97) is a prime
  @Test
  public void testFindPrimesUpTo97() {
    runCompareResultsTest(primeCalc, 97, ImmutableSortedSet.copyOf(asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,
        67, 71, 73, 79, 83, 89, 97)));
  }

  @Test
  public void testFindPrimesUpTo200() {
    runCompareResultsTest(primeCalc, 200, ImmutableSortedSet.copyOf(asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,
        67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
        181, 191, 193, 197, 199)));
  }

  // there are 664,579 primes <= 10 million
  // @see https://primes.utm.edu/howmany.html
  @Test
  public void testFindPrimesUpToTenMillion() {
    runCountResultsTest(primeCalc, 10_000_000, 664_579);
  }

  // there are 50,847,534 primes <= one billion
  // @see https://primes.utm.edu/howmany.html
  @Ignore("time-consuming and memory-intensive test - allocate a large max heap e.g. 8GB")
  @Test
  public void testFindPrimesUpToOneBillion() {
    runCountResultsTest(primeCalc, 1_000_000_000, 50_847_534);
  }

  private void runCompareResultsTest(final PrimeCalculator primeCalc, final int upperBound, final Set<Integer> expectedResult) {
    Set<Integer> result = primeCalc.findPrimes(upperBound);
    assertEquals(expectedResult, result);
  }

  private void runCountResultsTest(final PrimeCalculator primeCalc, final int upperBound, final int expectedSize) {
    Set<Integer> results = primeCalc.findPrimes(upperBound);
    assertThat(results, hasSize(expectedSize));
  }

}
