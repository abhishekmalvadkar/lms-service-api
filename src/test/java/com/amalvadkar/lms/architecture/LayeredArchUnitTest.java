package com.amalvadkar.lms.architecture;

import com.amalvadkar.lms.common.AbstractArchUnitTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class LayeredArchUnitTest extends AbstractArchUnitTest {

    @Test
    void controllers_should_not_depend_on_repositories(){
        ArchRule noControllerToRepoCallRule = noClasses()
                .that()
                .resideInAPackage("..controllers..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..repositories..")
                .because("Controllers should only depend on services and not directly on repositories");

        noControllerToRepoCallRule.check(importedClasses);
    }

}
