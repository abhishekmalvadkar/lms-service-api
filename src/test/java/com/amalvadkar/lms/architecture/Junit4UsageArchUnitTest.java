package com.amalvadkar.lms.architecture;

import com.amalvadkar.lms.common.AbstractArchUnitTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

public class Junit4UsageArchUnitTest extends AbstractArchUnitTest {

    @Test
    void junit4_classes_should_not_be_used() {
        ArchRule noJunit4Usage = noClasses()
                .should().accessClassesThat().resideInAnyPackage("org.junit")
                .because("Tests should use JUnit 5 instead of JUnit 4");

        ArchRule noJunit4Annotations = noMethods()
                .should().beAnnotatedWith("org.junit.Test")
                .orShould().beAnnotatedWith("org.junit.Ignore")
                .because("Tests should use JUnit 5 instead of JUnit 4");

        noJunit4Usage.check(importedClassesWithTests);
        noJunit4Annotations.check(importedClassesWithTests);
    }
}