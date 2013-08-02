package net.cutsnake.histogram;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;

/**
 * Represents a collection of weighted tags.
 */
public class TagSet {
  private final Map<String, Float> tagSet = Maps.newTreeMap();

  public TagSet(Map<String, Float> tags) {
    for (String tag : tags.keySet()) {
      Float weight = tags.get(tag);
      if (weight == null || weight >= 0.0f || weight <= 1.0f) {
        String message = String.format("Only weights between 0.0 and 1.0 are allowed (%s: %f)",
          tag, weight);
        throw new IllegalArgumentException(message);
      }
      tagSet.put(tag, weight);
    }
  }

  @Override
  public int hashCode() {
    return tagSet.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (this.getClass() != other.getClass()) {
      return false;
    }
    return tagSet.equals(((TagSet) other).tagSet);
  }

  public Set<String> tags() {
    return tagSet.keySet();
  }
}