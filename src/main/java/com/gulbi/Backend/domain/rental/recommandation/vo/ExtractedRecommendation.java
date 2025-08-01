package com.gulbi.Backend.domain.rental.recommandation.vo;

import java.util.*;

public class ExtractedRecommendation {
    private final Map<Integer, Map<String,Integer>> recommendationIndices;

    private ExtractedRecommendation(List<String> bCategoryList, List<String> mCategoryList, List<String> priorityList) {
        List<Integer> bCategories = convertStringListToIntegerList(bCategoryList);
        List<Integer> mCategories = convertStringListToIntegerList(mCategoryList);
        List<Integer> priority = dupulicatedListSolution(convertStringListToIntegerList(priorityList));
        this.recommendationIndices = createRecommandationMap(bCategories, mCategories, priority);
    }

    public static ExtractedRecommendation of(List<String> bCategoryList, List<String> mCategoryList, List<String> priorityList) {
        return new ExtractedRecommendation(bCategoryList, mCategoryList, priorityList);
    }

    private List<Integer> convertStringListToIntegerList(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();
        for (String s : stringList) {
            integerList.add(Integer.parseInt(s));
        }
        return integerList;
    }

    private Map<Integer, Map<String, Integer>> createRecommandationMap(List<Integer> bCategoryList, List<Integer> mCategoryList, List<Integer> priorityList) {
        Map<Integer, Map<String,Integer>> recommandationMap = new HashMap<>();
        if (bCategoryList.size() != priorityList.size() || mCategoryList.size() != priorityList.size()) {
            throw new IllegalArgumentException("bCategoryList, mCategoryList, priorityList 크기가 다릅니다.");
        }

        for (int i = 0; i < priorityList.size(); i++) {
            int priority = priorityList.get(i);
            Map<String, Integer> categoryMap = new HashMap<>();
            categoryMap.put("bCategories", bCategoryList.get(i));
            categoryMap.put("mCategories", mCategoryList.get(i));
            recommandationMap.put(priority, categoryMap);
        }

        return recommandationMap;
    }

    private List<Integer> dupulicatedListSolution(List<Integer> list){
        list.sort(Comparator.reverseOrder());
        for (int i = 0; i < list.size(); i++) {
            if (i > 0 && list.get(i).equals(list.get(i - 1))) {
                for (int j = 0; j < i; j++) {
                    list.set(j, list.get(j) + 1);
                }
            }
        }
        return list;
    }

    public List<Integer> getSortedKey() {
        List<Integer> sortedKeys = new ArrayList<>(this.recommendationIndices.keySet());
        sortedKeys.sort(Comparator.reverseOrder());
        return sortedKeys;
    }

    public Integer getBCategory(int priority){
        return this.recommendationIndices.get(priority).get("bCategories");
    }

    public Integer getMCategory(int priority){
        return this.recommendationIndices.get(priority).get("mCategories");
    }

    public void printRecommendationIndices() {
        this.recommendationIndices.forEach((priority, categoryMap) -> {
            System.out.println("Priority: " + priority);
            categoryMap.forEach((categoryType, value) -> {
                System.out.println("  " + categoryType + ": " + value);
            });
        });
    }
}
