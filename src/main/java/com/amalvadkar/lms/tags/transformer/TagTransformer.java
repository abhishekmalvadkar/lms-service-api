package com.amalvadkar.lms.tags.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public interface TagTransformer {

    String ANY_NUMBER_OF_WHITESPACE_REGEX = "\\s+";
    String DASH = "-";
    Logger log = LoggerFactory.getLogger(TagTransformer.class);

    /**
     * Responsible to convert user entered tag name into dashed tag name along with lowercase
     * e.g. i/p = Spring Boot -> o/p = spring-boot
     *
     * @param tagName User entered tag name
     *
     * @return The dashed lowercase tag name
     */
    static String transformTag(String tagName) {
        Objects.requireNonNull(tagName, "Tag name should not be null to transform");
        log.info("Input tag name : {}" , tagName);
        String transformedTagName = tagName.toLowerCase().replaceAll(ANY_NUMBER_OF_WHITESPACE_REGEX, DASH);
        log.info("Transformed tag name : {}" , transformedTagName);
        return transformedTagName;
    }

}
