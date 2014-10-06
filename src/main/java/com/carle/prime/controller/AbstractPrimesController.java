package com.carle.prime.controller;

import static com.google.common.base.Stopwatch.createStarted;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carle.prime.calc.PrimeCalculator;
import com.carle.prime.model.PrimesResponse;
import com.google.common.base.Stopwatch;

/**
 * Abstract base class for Primes controller classes. Create a subclass for each
 * PrimeCalculator implementation.
 */
public abstract class AbstractPrimesController {
  private static final Logger logger = LoggerFactory.getLogger(AbstractPrimesController.class);

  /**
   * Returns a list of all prime numbers up to and including the supplied upper bound.
   */
  public PrimesResponse primes(final Integer upperBound) {
    Stopwatch stopwatch = createStarted();
    PrimesResponse response = new PrimesResponse(upperBound, getPrimeCalculator().findPrimes(upperBound));
    logger.info("Elapsed time {} ms: {}", stopwatch.elapsed(MILLISECONDS), response);

    return response;
  }

  protected abstract PrimeCalculator getPrimeCalculator();
}
