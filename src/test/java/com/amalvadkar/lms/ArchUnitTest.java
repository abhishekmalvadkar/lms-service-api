package com.amalvadkar.lms;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

public class ArchUnitTest {

    JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.amalvadkar.lms");

    @Test
    void should_not_use_field_injection() {
        noFields().should()
                .beAnnotatedWith(Autowired.class)
                .because("We agreed on constructor injection as recommend approach")
                .check(importedClasses);
    }

    @Test
    void should_not_use_junit4_classes() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.amalvadkar.lms");

        noClasses()
                .should()
                .accessClassesThat()
                .resideInAnyPackage("org.junit")
                .because("Tests should use Junit5 instead of Junit4")
                .check(classes);

        noMethods()
                .should()
                .beAnnotatedWith("org.junit.Test")
                .orShould()
                .beAnnotatedWith("org.junit.Ignore")
                .because("Tests should use Junit5 instead of Junit4")
                .check(classes);
    }

}
