/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken

import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.bouncycastle.pkcs.PKCS10CertificationRequest
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.GostDemoCA.issueCertificate
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue.CKR_CORRUPTED_MAPFILE

class RtExtendedTest {
    @Test
    fun getTokenInfoExtended() {
        val info = token.value.tokenInfoExtended
    }

    @Test
    fun createCsr() {
        val csr = session.value.createCsr(keyPair.value.publicKey, DN, keyPair.value.privateKey, ATTRIBUTES, EXTENSIONS)
        val encodedCertificate = issueCertificate(csr)
    }

    @Test
    fun createCsrWithNullDn() {
        val csr =
            session.value.createCsr(keyPair.value.publicKey, null, keyPair.value.privateKey, ATTRIBUTES, EXTENSIONS)
        val encodedCertificate = issueCertificate(csr)
    }

    @Test
    fun createCsrWithNullExtensions() {
        val csr = session.value.createCsr(keyPair.value.publicKey, DN, keyPair.value.privateKey, ATTRIBUTES, null)
        val encodedCertificate = issueCertificate(csr)
    }

    @Test
    fun checkAttributesInCertificationRequest() {
        val csr = session.value.createCsr(keyPair.value.publicKey, DN, keyPair.value.privateKey, ATTRIBUTES, EXTENSIONS)
        val expectedAttributes = buildList<String> {
            PKCS10CertificationRequest(csr).attributes.forEach { attribute ->
                attribute.attributeValues.forEach { attributeValue ->
                    add(attribute.attrType.id)
                    add(attributeValue.toString())
                }
            }
        }
        expectedAttributes shouldContainInOrder ATTRIBUTES
    }

    @Test
    fun vendorReturnValue() {
        val value = IPkcs11ReturnValue.getInstance(CKR_CORRUPTED_MAPFILE.asLong, module.value.vendorExtensions)
        value shouldBe CKR_CORRUPTED_MAPFILE
    }

    @Test
    fun getCertificateInfoText() {
        val certificateInfo = session.value.getCertificateInfo(certificate.value)
        certificateInfo.isNullOrEmpty() shouldBe false
    }

    companion object {
        private val module = RtModuleRule()
        private val attributeFactory = module.value.attributeFactory
        private val slot = SlotRule(module)
        private val token = RtTokenRule(slot)
        private val session = RtSessionRule(token)
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)
        private val keyPair = attributeFactory.makeGostR3410_2012_256KeyPairRule(session)
        private val certificate =
            CreateGostCertificateRule(session, keyPair, "GOST R 34.10-2012 (256 bits) Certificate")

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session)
            .around(login).around(keyPair).around(certificate)
    }
}