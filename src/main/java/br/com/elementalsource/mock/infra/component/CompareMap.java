package br.com.elementalsource.mock.infra.component;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;

@Component
public class CompareMap {

    public <K, V extends Comparable<V>> boolean isEquivalent(Multimap<K, V> value, Multimap<K, V> valueToCompare) {

            for(Map.Entry<K, Collection<V>> entry : value.asMap().entrySet()){
                final K key = entry.getKey();
                if (!valueToCompare.containsKey(key)) {
                    return false;
                }

                final Collection<V> valueCompare = valueToCompare.get(key);

                if(!equalCollections(entry.getValue(), valueCompare)){
                    return false;
                }

            }

        return true;
    }

    private <V extends Comparable<V>> boolean equalCollections(Collection<V> one, Collection<V> two){
        if (one == null && two == null){
            return true;
        }

        if((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()){
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        List<V> first = new ArrayList<>(one);
        List<V> second = new ArrayList<>(two);

        Collections.sort(first);
        Collections.sort(second);
        return first.equals(second);
    }

}
