package net.cutsnake.histogram;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import net.cutsnake.histogram.TagSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.testing.EqualsTester;

import java.util.Map;

@RunWith(JUnit4.class)
public class TagSetTest {
  @Rule public ExpectedException thrown = ExpectedException.none();
  
  @Test public void constructor_trimsNames() {
    Map<String, Float> tags = ImmutableMap.<String, Float>of("foo ", 1.0f);

    TagSet set = new TagSet(tags);

    assertThat(set.tags(), hasItem("foo"));    
  }

  @Test public void constructor_convertsToLowerCase() {
    Map<String, Float> tags = ImmutableMap.<String, Float>of("FOO", 1.0f);

    TagSet set = new TagSet(tags);

    assertThat(set.tags(), hasItem("foo"));    
  }

  @Test public void constructor_disallowsEmptyNames() {
    thrown.expect(IllegalArgumentException.class);
    Map<String, Float> tags = ImmutableMap.<String, Float>of("  ", 1.0f);

    new TagSet(tags);
  }

  @Test public void constructor_disallowsNullWeights() {
    thrown.expect(IllegalArgumentException.class);
    Map<String, Float> tags = Maps.newHashMap(); // ImmutableMap does not allow null values.
    tags.put("foo", null);

    new TagSet(tags);
  }

  @Test public void constructor_disallowsNegativeWeights() {
    thrown.expect(IllegalArgumentException.class);
    Map<String, Float> tags = ImmutableMap.<String, Float>of("foo", -1.0f);

    new TagSet(tags);
  }

  @Test public void constructor_disallowsWeightsOverOne() {
    thrown.expect(IllegalArgumentException.class);
    Map<String, Float> tags = ImmutableMap.<String, Float>of("foo", 1.1f);

    new TagSet(tags);
  }

  @Test public void equals() {
    TagSet tagSet1 = new TagSet(ImmutableMap.<String, Float>of("foo", 1.0f));
    TagSet tagSet2 = new TagSet(ImmutableMap.<String, Float>of("foo ", 1.0f));
    TagSet tagSet3 = new TagSet(ImmutableMap.<String, Float>of("FOO", 1.0f));
    TagSet tagSuperSet = new TagSet(ImmutableMap.<String, Float>of("foo", 1.0f, "bar", 1.0f));
    TagSet tagSetWithDifferentWeight = new TagSet(ImmutableMap.<String, Float>of("foo", 0.5f));
    TagSet tagSetWithDifferentName = new TagSet(ImmutableMap.<String, Float>of("bar", 1.0f));

    new EqualsTester()
        .addEqualityGroup(tagSet1, tagSet2, tagSet3)
        .addEqualityGroup(tagSuperSet)
        .addEqualityGroup(tagSetWithDifferentWeight)
        .addEqualityGroup(tagSetWithDifferentName)
        .testEquals();
   }
}
