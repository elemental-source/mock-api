package br.com.elementalsource.mock.infra.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class UriConfiguration implements Serializable {

    private String host;
    private Pattern pattern;
    private Boolean backup;
    private String prefixPath;

    public UriConfiguration(String host, Pattern pattern, Boolean backup, String prefixPath) {
        this.host = host;
        this.pattern = pattern;
        this.backup = backup;
        this.prefixPath = prefixPath;
    }

    public UriConfiguration() {
        this.backup = true;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Pattern getPattern() {
        return pattern;
    }

	public void setPrefixPath(final String prefixPath) {
		this.prefixPath = prefixPath;
	}

	public String getPrefixPath() {
        return prefixPath;
    }

    public void setPattern(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    }

    public Boolean isActiveBackup() {
        return Optional.ofNullable(backup).orElse(true);
    }

    public void setBackup(Boolean backup) {
        this.backup = backup;
    }

    @Override
    public String toString() {
        return "UriConfiguration [host=" + host + ", pattern=" + pattern + ", backup=" + backup + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UriConfiguration that = (UriConfiguration) o;
        return Objects.equals(host, that.host) &&
                Objects.equals(pattern.pattern(), that.pattern.pattern()) &&
                Objects.equals(pattern.flags(), that.pattern.flags()) &&
                Objects.equals(backup, that.backup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, pattern, backup);
    }
}
