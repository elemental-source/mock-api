package br.com.elementalsource.mock.infra.component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CompareMapTest {

    @InjectMocks
    private CompareMap compareMap;

    @Test
    public void shouldBeEqualsWhenCompareValueMaps() {
        // given
        final Multimap<String, String> map = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .build();
        final Multimap<String, String> mapToCompare = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertTrue(isEquivalent);
    }

    @Test
    public void shouldBeEqualsWhenCompareValueMapsWhereHaveMoreAttributesInComparation() {
        // given
        final Multimap<String, String> map = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .build();
        final Multimap<String, String> mapToCompare = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .put("age", "15")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertTrue(isEquivalent);
    }

    @Test
    public void shouldNotBeEqualsWhenCompareValueMapsWhereHaveLessAttributesInComparation() {
        // given
        final Multimap<String, String> map = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .put("age", "15")
                .build();
        final Multimap<String, String> mapToCompare = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertFalse(isEquivalent);
    }

    @Test
    public void shouldNotBeEqualsWhenThereDifferentValues() {
        // given
        final Multimap<String, String> map = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .put("age", "15")
                .build();
        final Multimap<String, String> mapToCompare = ImmutableMultimap.<String,String>builder()
                .put("name", "Paul")
                .put("age", "25")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertFalse(isEquivalent);
    }

}
