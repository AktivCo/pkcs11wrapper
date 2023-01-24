/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.ktx

import io.kotest.matchers.shouldNotBe
import org.junit.Test
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_DECRYPT
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_DERIVE
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule

class TemplateBuilderTest {
    @Test
    fun build() {
        val template = TemplateBuilder.build(module.value.attributeFactory) {
            decrypt().derive()
        }

        template.find { it.type == CKA_DECRYPT && it.value == true } shouldNotBe null
        template.find { it.type == CKA_DERIVE && it.value == true } shouldNotBe null
    }

    @Test
    fun buildJavaStyle() {
        val template = TemplateBuilder(module.value.attributeFactory).decrypt().build()

        template.find { it.type == CKA_DECRYPT && it.value == true } shouldNotBe null
    }

    companion object {
        private val module = ModuleRule()
    }
}