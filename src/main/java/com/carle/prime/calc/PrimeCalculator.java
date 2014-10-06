package com.carle.prime.calc;

import java.util.Set;

/**
 * Provides methods for finding prime numbers.
 *
 * @author Matt Carle
 */
public interface PrimeCalculator {
  /**
   * Return a list of all prime numbers less than or equal to <i>upperBound</i>.
   */
  Set<Integer> findPrimes(int upperBound);
}