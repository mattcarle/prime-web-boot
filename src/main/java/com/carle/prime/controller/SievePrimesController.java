package com.carle.prime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carle.prime.calc.PrimeCalculator;
import com.carle.prime.calc.SievePrimeCalculator;
import com.carle.prime.model.PrimesResponse;

/**
 * Controller for requests to the Sieve prime calculator.
 *
 */
@RestController
public class SievePrimesController extends AbstractPrimesController {
  @Autowired
  @Qualifier("sievePrimeCalculator")
  private SievePrimeCalculator primeCalculator;

  @Override
  protected PrimeCalculator getPrimeCalculator() {
    return primeCalculator;
  }

  @Override
  @RequestMapping(value = "/primes/{upperBound}", method = RequestMethod.GET)
  public PrimesResponse primes(@PathVariable("upperBound") final Integer upperBound) {
    return super.primes(upperBound);
  }
}
