package com.recipemanager.favouriterecipeservice.config;

public enum SearchOperation {

    CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL,
    NUL, NOT_NULL,
    ANY, ALL;
    public static final String[] SIMPLE_OPERATION_SET = {
            "cn", "nc", "eq", "ne", "nu", "nn"};

    public static SearchOperation getDataOption(final String
                                                        dataOption) {
        switch (dataOption) {
            case "all":
                return ALL;
            case "any":
                return ANY;
            default:
                return null;
        }
    }

    public static SearchOperation getSimpleOperation(
            final String input) {
        switch (input) {
            case "cn":
                return CONTAINS;
            case "nc":
                return DOES_NOT_CONTAIN;
            case "eq":
                return EQUAL;
            case "ne":
                return NOT_EQUAL;
            case "nu":
                return NUL;
            case "nn":
                return NOT_NULL;
            default:
                return null;
        }
    }
}