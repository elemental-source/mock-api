package br.com.elementalsource.mock.infra.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompareJson {

    private final ConvertJson convertJson;

    @Autowired
    public CompareJson(ConvertJson convertJson) {
        this.convertJson = convertJson;
    }

    public Boolean isEquivalent(String jsonKey, String jsonToCompare) {
        final HashMap<String, Object> keys = convertJson.apply(jsonKey);
        final HashMap<String, Object> toCompare = convertJson.apply(jsonToCompare);
        return compareMaps(keys, toCompare);
    }

    private boolean compareMaps(Map<String, Object> keys, Map<String, Object> toCompare) {
        for (Object entrySet : keys.entrySet()) {
            Map.Entry entry = (Map.Entry) entrySet;
            final Object value = entry.getValue();
            final Object valueCompare = toCompare.get(entry.getKey());

            if(valueCompare instanceof Map && value instanceof Map){
                boolean compareResult = compareMaps((Map)value, (Map)valueCompare);
                if(compareResult == false) {
                    return false;
                }
            } else if (valueCompare instanceof List && value instanceof List) {
                boolean compareResult = compareLists((List)value, (List)valueCompare);
                if(compareResult == false) {
                    return false;
                }
            } else if (!entry.getValue().equals(valueCompare)) {
                return false;
            }
        }

        return true;
    }

    private boolean compareLists(List list, List toCompare) {
		List first = new ArrayList<>(list);
		List second = new ArrayList<>(toCompare);
        for (int i = 0; i < first.size(); i++) {
            final Object value = first.get(i);
			boolean compareResult = false;
			Iterator iterator = second.iterator();
			while(iterator.hasNext()) {
				final Object valueCompare = iterator.next();
				if(valueCompare instanceof List && value instanceof List) {
					compareResult = compareLists((List)value, (List)valueCompare);
					if(compareResult) {
						iterator.remove();
						break;
					}

				} else if (valueCompare instanceof Map && value instanceof Map) {
					compareResult = compareMaps((Map)value, (Map)valueCompare);
					if(compareResult) {
						iterator.remove();
						break;
					}
				} else if (!value.equals(valueCompare)) {
					return false;
				}
			}

			if(!compareResult) {
				return false;
			}

        }
        return true;
    }

}
