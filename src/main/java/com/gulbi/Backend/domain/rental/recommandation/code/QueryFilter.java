package com.gulbi.Backend.domain.rental.recommandation.code;

public enum QueryFilter {
        VIEWED("viewedQueryStrategy"),
        RECENT("latestProductQueryStrategy");

        private final String strategyBeanName;

        QueryFilter(String strategyBeanName) {
                this.strategyBeanName = strategyBeanName;
        }

        public String getStrategyBeanName() {
                return strategyBeanName;
        }
}
