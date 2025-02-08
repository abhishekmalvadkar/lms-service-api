package com.amalvadkar.lms.architecture;

import com.amalvadkar.lms.common.AbstractArchUnitTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

public class FieldInjectionArchUnitTest extends AbstractArchUnitTest {

    @Test
    void field_injection_should_not_be_used() {
        ArchRule noFieldInjection = noFields()
                .should().beAnnotatedWith(Autowired.class)
                .because("Constructor injection is the recommended approach");

        noFieldInjection.check(importedClasses);
    }

}
