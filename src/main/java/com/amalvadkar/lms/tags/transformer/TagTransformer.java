package com.amalvadkar.lms.tags.transformer;

public interface TagTransformer {

    String ANY_NUMBER_OF_WHITESPACE_REGEX = "\\s+";
    String DASH = "-";

    /**
     * Responsible to convert user entered tag name into dashed tag name along with lowercase
     * e.g. i/p = Spring Boot -> o/p = spring-boot
     *
     * @param tagName User entered tag name
     *
     * @return The dashed lowercase tag name
     */
    static String transformTag(String tagName) {
        return tagName.toLowerCase().replaceAll(ANY_NUMBER_OF_WHITESPACE_REGEX, DASH);
    }

}
