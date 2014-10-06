package com.carle.prime.model;

import static com.carle.prime.util.Util.truncListToString;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.carle.prime.util.Util;
import com.google.common.base.Joiner;

/**
 * Used to represent the response passed back to the client. JSON conversion works
 * out-of-the-box. Annotations are provided for XML conversion.
 */
@XmlRootElement
public class PrimesResponse {
  private static final int MAX_TO_DISPLAY = 100;
  private Integer initial;
  private Set<Integer> primes;

  // default constructor required for XML marshalling
  public PrimesResponse() {
  }

  public PrimesResponse(final Integer initial, final Set<Integer> primes) {
    this.initial = initial;
    this.primes = primes;
  }

  @XmlElement
  public Integer getInitial() {
    return initial;
  }

  public void setInitial(final Integer initial) {
    this.initial = initial;
  }

  @XmlElementWrapper(name = "primes")
  @XmlElement(name = "prime")
  public Set<Integer> getPrimes() {
    return primes;
  }

  public void setPrimes(final Set<Integer> primes) {
    this.primes = primes;
  }

  @Override
  public String toString() {
    return String.format("Found %d prime numbers less than or equal to %d: [%s]", primes.size(), initial,
        truncListToString(primes, MAX_TO_DISPLAY));
  }
}
