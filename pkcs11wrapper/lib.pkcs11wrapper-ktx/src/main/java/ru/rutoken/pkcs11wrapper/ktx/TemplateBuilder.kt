package ru.rutoken.pkcs11wrapper.ktx

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11CertificateType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ObjectClass
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*

interface ITemplateBuilder<Builder : ITemplateBuilder<Builder>> {
    val template: MutableList<Pkcs11Attribute>
    val attributeFactory: IPkcs11AttributeFactory

    fun self(): Builder

    fun add(type: IPkcs11AttributeType, value: Any): Builder {
        template.add(attributeFactory.attribute(type, value))
        return self()
    }

    fun addAll(attributes: Iterable<Pkcs11Attribute>): Builder {
        template.addAll(attributes)
        return self()
    }

    fun objectClass(value: IPkcs11ObjectClass) = add(CKA_CLASS, value)
    fun keyType(value: IPkcs11KeyType) = add(CKA_KEY_TYPE, value)
    fun certificateType(value: IPkcs11CertificateType) = add(CKA_CERTIFICATE_TYPE, value)
    fun label(value: String) = add(CKA_LABEL, value)
    fun sign(value: Boolean = true) = add(CKA_SIGN, value)
    fun verify(value: Boolean = true) = add(CKA_VERIFY, value)
    fun derive(value: Boolean = true) = add(CKA_DERIVE, value)
    fun wrap(value: Boolean = true) = add(CKA_WRAP, value)
    fun unwrap(value: Boolean = true) = add(CKA_UNWRAP, value)
    fun encrypt(value: Boolean = true) = add(CKA_ENCRYPT, value)
    fun decrypt(value: Boolean = true) = add(CKA_DECRYPT, value)
    fun token(value: Boolean = true) = add(CKA_TOKEN, value)
    fun extractable(value: Boolean = true) = add(CKA_EXTRACTABLE, value)
    fun sensitive(value: Boolean = true) = add(CKA_SENSITIVE, value)
    fun trusted(value: Boolean = true) = add(CKA_TRUSTED, value)
    fun private(value: Boolean = true) = add(CKA_PRIVATE, value)
    fun value(value: ByteArray) = add(CKA_VALUE, value)
    fun id(value: ByteArray) = add(CKA_ID, value)
    fun gost3410params(value: ByteArray) = add(CKA_GOSTR3410_PARAMS, value)
    fun gost3411params(value: ByteArray) = add(CKA_GOSTR3411_PARAMS, value)
    //TODO add more required functions

    fun build() = template
}

abstract class BaseTemplateBuilder<Builder : ITemplateBuilder<Builder>> : ITemplateBuilder<Builder> {
    override val template = mutableListOf<Pkcs11Attribute>()
}

/**
 * Concrete class for building templates for any objects
 */
class TemplateBuilder(override val attributeFactory: IPkcs11AttributeFactory) : BaseTemplateBuilder<TemplateBuilder>() {
    override fun self() = this

    companion object {
        fun build(attributeFactory: IPkcs11AttributeFactory, fillTemplate: TemplateBuilder.() -> Unit) =
            with(TemplateBuilder(attributeFactory)) {
                fillTemplate()
                build()
            }
    }
}