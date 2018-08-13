package br.com.elementalsource.mock.generic.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.MoreObjects;

public class ExistentFile implements Comparable<ExistentFile> {

	private final Pattern regex;
	private final String originalPath;

	ExistentFile(String path) {
		originalPath = path;
		regex = Pattern.compile(path.replaceAll("#\\{\\{\\w*\\}\\}", "(.*)"));
	}

	public PathParamExtractor matches(String rawPath) {
		return new PathParamExtractor(rawPath);
	}

	public String getOriginalPath() {
		return originalPath;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("regex", regex)
				.add("originalPath", originalPath)
				.toString();
	}

	@Override
	public int compareTo(final ExistentFile o) {
		return this.originalPath.compareTo(o.originalPath) * -1;
	}

	public class PathParamExtractor {

		private boolean matchs;
		private Map<String, String> parameters = new HashMap<>();

		public PathParamExtractor(String rawPath ) {
			Matcher rawPathMatcher = regex.matcher(rawPath);
			Matcher originalPathMatcher = regex.matcher(originalPath);

			matchs = rawPathMatcher.matches() && originalPathMatcher.matches();

			if(matchs){
				for (int i = 1; i <= rawPathMatcher.groupCount(); i++){
					parameters.put(originalPathMatcher.group(i).replaceAll("\\W", ""),rawPathMatcher.group(i));
				}

			}
		}

		public boolean isMatchs() {
			return matchs;
		}

		public Map<String, String> getParameters() {
			return parameters;
		}

		public String getOriginalPath(){
			return originalPath;
		}
	}



}
