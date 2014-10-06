package com.carle.prime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carle.prime.calc.CachingSievePrimeCalculator;
import com.carle.prime.calc.PrimeCalculator;
import com.carle.prime.model.PrimesResponse;

/**
 * Controller for requests to the Caching Sieve prime calculator.
 *
 */
@RestController
public class CachingSievePrimesController extends AbstractPrimesController {
  @Autowired
  @Qualifier("cachingSievePrimeCalculator")
  private CachingSievePrimeCalculator primeCalculator;

  @Override
  protected PrimeCalculator getPrimeCalculator() {
    return primeCalculator;
  }

  @Override
  @RequestMapping(value = "/cache/primes/{upperBound}", method = RequestMethod.GET)
  public PrimesResponse primes(@PathVariable("upperBound") final Integer upperBound) {
    return super.primes(upperBound);
  }
}
