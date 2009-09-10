package com.rjcass.diff.comparator;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexComparator implements Comparator<CharSequence>
{
	public static final String MATCH_ALL = ".*";
	public static final String IGNORE_WHITESPACE = "";
	private Pattern mPattern;
	private boolean mIgnoreCase;

	public RegexComparator()
	{
		this("^.*$");
	}

	public RegexComparator(String pattern)
	{
		this(pattern, false);
	}

	/**
	 * Creates a comparator with the given pattern and ignore case indicator.
	 * 
	 * @param pattern
	 * @param ignoreCase
	 * 
	 * @see setIgnoreCase(boolean)
	 * @see setPattern(String)
	 */
	public RegexComparator(String pattern, boolean ignoreCase)
	{
		setPattern(pattern);
		setIgnoreCase(ignoreCase);
	}

	public void setPattern(String pattern)
	{
		mPattern = Pattern.compile(pattern);
	}

	/**
	 * Set the ignore case indicator.
	 * 
	 * If true, case is ignored in the final string comparison. Note that the
	 * regular expression matching is STILL case-sensitive.
	 * 
	 * @param ignoreCase
	 *            true is case should be ignored in the final comparison.
	 */
	public void setIgnoreCase(boolean ignoreCase)
	{
		mIgnoreCase = ignoreCase;
	}

	@Override
	public int compare(CharSequence s1, CharSequence s2)
	{
		Matcher m1 = mPattern.matcher(s1);
		Matcher m2 = mPattern.matcher(s2);

		m1.matches();
		m2.matches();

		String g1 = m1.group();
		String g2 = m2.group();

		if (mIgnoreCase)
			return g1.compareToIgnoreCase(g2);
		else
			return g1.compareTo(g2);
	}
}
