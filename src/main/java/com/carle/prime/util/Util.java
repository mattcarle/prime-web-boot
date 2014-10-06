package com.carle.prime.util;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

/**
 * Miscellaneous utility methods
 *
 * @author Matt Carle
 *
 */
public class Util {

  /**
   * Convert the <i>list</i> to a String, with values comma-separated. If the size of <i>list</i> exceeds <i>max</i>,
   * then only output the first <i>max</i> items, followed by an ellipsis.
   */
  public static <T> String truncListToString(final Set<T> items, final int maxToDisplay) {
    Joiner joiner = Joiner.on(", ");
    List<T> list = items.stream().limit(maxToDisplay + 1).collect(toList());
    return list.size() > maxToDisplay ? joiner.join(list.subList(0, maxToDisplay)) + ", ..." : joiner.join(list);
  }
}
