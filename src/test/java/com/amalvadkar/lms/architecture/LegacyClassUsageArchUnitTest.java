package com.amalvadkar.lms.architecture;

import com.amalvadkar.lms.common.AbstractArchUnitTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class LegacyClassUsageArchUnitTest extends AbstractArchUnitTest {

    @Test
    void should_not_use_java_util_date(){
        ArchRule noDataLegacyClassUseRule = noClasses()
                .should()
                .dependOnClassesThat()
                .haveFullyQualifiedName("java.util.Date")
                .because("Use java.time.LocalDate or java.time.Instant instead of java.util.Date");

        noDataLegacyClassUseRule.check(importedClassesWithTests);
    }

}
