package com.amalvadkar.lms.common;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;

public class AbstractArchUnitTest {

    protected static final String BASE_PACKAGE = "com.amalvadkar.lms";
    protected static JavaClasses importedClasses;
    protected static JavaClasses importedClassesWithTests;

    @BeforeAll
    static void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE);

        importedClassesWithTests = new ClassFileImporter()
                .importPackages(BASE_PACKAGE);
    }

}
