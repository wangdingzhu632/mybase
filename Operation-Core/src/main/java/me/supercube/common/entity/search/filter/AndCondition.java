package me.supercube.common.entity.search.filter;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * and 条件
 * */
public class AndCondition implements SearchFilter {
	private List<SearchFilter> andFilters = Lists.newArrayList();

	AndCondition() {
	}

	public AndCondition add(SearchFilter filter) {
		this.andFilters.add(filter);
		return this;
	}

	public List<SearchFilter> getAndFilters() {
		return andFilters;
	}

	@Override
	public String toString() {
		return "AndCondition{" + andFilters + '}';
	}
}
