/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2023 Adobe
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package com.adobe.cq.forms.core.components.internal.models.v1.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import com.adobe.aemds.guide.service.GuideException;
import com.adobe.cq.forms.core.Utils;
import com.adobe.cq.forms.core.components.internal.form.FormConstants;
import com.adobe.cq.forms.core.components.models.form.*;
import com.adobe.cq.forms.core.context.FormsCoreComponentTestContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
public class RecaptchaImplTest {
    private static final String BASE = "/form/recaptcha";
    private static final String CONTENT_ROOT = "/content";
    private static final String PATH_RECAPTCHA = CONTENT_ROOT + "/recaptcha";

    private final AemContext context = FormsCoreComponentTestContext.newAemContext();

    @BeforeEach
    void setUp() throws GuideException {
        context.load().json(BASE + FormsCoreComponentTestContext.TEST_CONTENT_JSON, CONTENT_ROOT);

    }

    @Test
    void testExportedType() {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        assertEquals(FormConstants.RT_FD_FORM_RECAPTCHA_V1, recaptcha.getExportedType());
        Recaptcha recaptchaMock = Mockito.mock(Recaptcha.class);
        Mockito.when(recaptchaMock.getExportedType()).thenCallRealMethod();
        assertEquals("", recaptchaMock.getExportedType());
    }

    @Test
    void testFieldType() {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        assertEquals(FieldType.CAPTCHA.getValue(), recaptcha.getFieldType());
    }

    @Test
    void testGetName() {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        assertEquals("test-recaptcha", recaptcha.getName());
        Recaptcha recaptchaMock = Mockito.mock(Recaptcha.class);
        Mockito.when(recaptchaMock.getName()).thenCallRealMethod();
        assertEquals(null, recaptchaMock.getName());
    }

    @Test
    void testGetRecaptchaSize() {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        assertEquals("compact", recaptcha.getSize());
        Recaptcha recaptchaMock = Mockito.mock(Recaptcha.class);
        Mockito.when(recaptchaMock.getName()).thenCallRealMethod();
        assertEquals(null, recaptchaMock.getName());
    }

    @Test
    void testGetConfigurationPath() {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        assertEquals("v2checkbox", recaptcha.getCloudServicePath());
        Recaptcha recaptchaMock = Mockito.mock(Recaptcha.class);
        Mockito.when(recaptchaMock.getName()).thenCallRealMethod();
        assertEquals(null, recaptchaMock.getName());
    }

    @Test
    void testIsVisible() {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        assertEquals(true, recaptcha.isVisible());
        Recaptcha recaptchaMock = Mockito.mock(Recaptcha.class);
        Mockito.when(recaptchaMock.isVisible()).thenCallRealMethod();
        assertEquals(null, recaptchaMock.isVisible());
    }

    @Test
    void testIsEnabled() {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        assertEquals(true, recaptcha.isEnabled());
        Recaptcha recaptchaMock = Mockito.mock(Recaptcha.class);
        Mockito.when(recaptchaMock.isEnabled()).thenCallRealMethod();
        assertEquals(null, recaptchaMock.isEnabled());
    }

    @Test
    void testJSONExport() throws Exception {
        Recaptcha recaptcha = Utils.getComponentUnderTest(PATH_RECAPTCHA, Recaptcha.class, context);
        Utils.testJSONExport(recaptcha, Utils.getTestExporterJSONPath(BASE, PATH_RECAPTCHA));
    }

}
