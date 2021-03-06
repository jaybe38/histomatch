package net.cutsnake.histogram;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import net.cutsnake.histogram.Matcher;
import net.cutsnake.histogram.TagSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableMap;

@RunWith(JUnit4.class)
public class MatcherTest {
  private final TagSet TAG_SET = new TagSet(ImmutableMap.of("FOO", 1.0f));

  private Matcher matcher = new Matcher();

  @Test public void match_exactMatch() {
    HasTags candidate = new HasTags() {
      @Override public TagSet getTags() {
        return TAG_SET;
      }
    };
    matcher.addCandidate(candidate);

    assertThat(matcher.match(TAG_SET).isPresent(), is(true));
    assertThat(matcher.match(TAG_SET).get(), is(candidate));
  }
}