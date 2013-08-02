package net.cutsnake.histogram;

import net.cutsnake.histogram.HasTags;
import net.cutsnake.histogram.TagSet;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Matches a set of required tags to a closest candidate.
 */
public class Matcher {
  private final Set<? extends HasTags> candidates = Sets.newHashSet();

  public Optional<HasTags> match(TagSet required) {
    for (HasTags candidate : candidates) {
      if (candidate.getTags().equals(required)) {
        return Optional.from(candidate);
      }
    }
    return Optional.absent();
  }

  public void addCandidate(HasTags candidate) {
    candidates.add(candidate);
  }
}