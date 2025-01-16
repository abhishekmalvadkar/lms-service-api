package com.amalvadkar.lms.tags.transformer;

public interface TagTransformer {

    String ANY_NUMBER_OF_WHITESPACE_REGEX = "\\s+";
    String DASH = "-";

    static String transformTag(String tagName) {
        return tagName.toLowerCase().replaceAll(ANY_NUMBER_OF_WHITESPACE_REGEX, DASH);
    }

}
