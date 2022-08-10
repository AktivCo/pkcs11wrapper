package ru.rutoken.pkcs11wrapper.object.factory;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11BaseObject;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11WtlsCertificateObject;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11X509AttributeCertificateObject;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11X509PublicKeyCertificateObject;
import ru.rutoken.pkcs11wrapper.object.data.Pkcs11DataObject;
import ru.rutoken.pkcs11wrapper.object.domainparameters.Pkcs11DomainParametersObject;
import ru.rutoken.pkcs11wrapper.object.hardwarefeature.Pkcs11ClockObject;
import ru.rutoken.pkcs11wrapper.object.hardwarefeature.Pkcs11HardwareFeatureObject;
import ru.rutoken.pkcs11wrapper.object.hardwarefeature.Pkcs11MonotonicCounterObject;
import ru.rutoken.pkcs11wrapper.object.hardwarefeature.Pkcs11UserInterfaceObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost256PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost256PublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost512PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost512PublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11SecretKeyObject;
import ru.rutoken.pkcs11wrapper.object.mechanism.Pkcs11MechanismObject;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType;

public class Pkcs11ObjectFactory implements IPkcs11ObjectFactory {
    private final IPkcs11AttributeFactory mAttributeFactory;
    private final ObjectFactoryNode<?> mRootNode;

    public Pkcs11ObjectFactory(IPkcs11AttributeFactory factory) {
        mAttributeFactory = Objects.requireNonNull(factory);
        mRootNode = new ObjectFactoryNode<>(
                new ImplementationObjectMaker<>(Pkcs11Object.class, Pkcs11BaseObject.class),
                makeAttribute(Pkcs11AttributeType.CKA_CLASS)
        );

        registerClasses();
    }

    private Pkcs11Attribute makeAttribute(IPkcs11AttributeType type) {
        return mAttributeFactory.makeAttribute(type);
    }

    @Override
    public <Obj extends Pkcs11Object> Obj makeObject(Class<Obj> objectClass, long objectHandle) {
        try {
            // look for special ObjectMaker if objectClass registered
            final ObjectFactoryNode<Obj> classNode = findNodeByObjectClass(objectClass);
            final Obj object;
            if (null != classNode)
                object = classNode.getObjectMaker().make(objectHandle);
            else
                object = new ConstructorObjectMaker<>(objectClass).make(objectHandle);
            object.registerAttributes();
            return object;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pkcs11Object makeObject(Pkcs11Session session, long objectHandle) {
        final HierarchyObjectMakerVisitor visitor = new HierarchyObjectMakerVisitor(session, objectHandle);
        mRootNode.acceptVisitor(visitor);
        final Pkcs11Object object = visitor.getObject();
        object.registerAttributes();
        return object;
    }

    @Override
    public <Obj extends Pkcs11Object> List<Pkcs11Attribute> makeTemplate(Class<Obj> objectClass) {
        final ObjectFactoryNode<Obj> classNode = findNodeByObjectClass(objectClass);
        if (null == classNode)
            throw new RuntimeException("object class not registered " + objectClass);

        TemplateMakerVisitor makerVisitor = new TemplateMakerVisitor(mAttributeFactory);
        classNode.acceptVisitor(makerVisitor);
        return makerVisitor.getTemplate();
    }

    @Override
    public boolean isObjectClassRegistered(Class<? extends Pkcs11Object> objectClass) {
        return null != findNodeByObjectClass(objectClass);
    }

    @Override
    public List<Class<? extends Pkcs11Object>> getRegisteredObjectClasses() {
        final ObjectClassGetterVisitor visitor = new ObjectClassGetterVisitor();
        mRootNode.acceptVisitor(visitor);
        return visitor.getObjectClasses();
    }

    @Nullable
    private <Obj extends Pkcs11Object> ObjectFactoryNode<Obj> findNodeByObjectClass(Class<Obj> objectClass) {
        final NodeByObjectClassFinderVisitor<Obj> visitor = new NodeByObjectClassFinderVisitor<>(objectClass);
        mRootNode.acceptVisitor(visitor);
        return visitor.getNode();
    }

    /**
     * One class cannot not be used in multiple nodes,
     * like you can not have hierarchy like this: A -> B -> B, (B cannot be inherited from itself)
     */
    private void registerClasses() {
        ObjectFactoryNode<?> data = new ObjectFactoryNode<>(Pkcs11DataObject.class, null);
        ObjectFactoryNode<?> certificate = new ObjectFactoryNode<>(Pkcs11CertificateObject.class,
                makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_TYPE));
        ObjectFactoryNode<?> publicKey = new ObjectFactoryNode<>(Pkcs11PublicKeyObject.class,
                makeAttribute(Pkcs11AttributeType.CKA_KEY_TYPE));
        ObjectFactoryNode<?> privateKey = new ObjectFactoryNode<>(Pkcs11PrivateKeyObject.class,
                makeAttribute(Pkcs11AttributeType.CKA_KEY_TYPE));
        ObjectFactoryNode<?> secretKey = new ObjectFactoryNode<>(Pkcs11SecretKeyObject.class, null);
        ObjectFactoryNode<?> hwFeature = new ObjectFactoryNode<>(Pkcs11HardwareFeatureObject.class,
                makeAttribute(Pkcs11AttributeType.CKA_HW_FEATURE_TYPE));
        ObjectFactoryNode<?> domainParameters = new ObjectFactoryNode<>(Pkcs11DomainParametersObject.class, null);
        ObjectFactoryNode<?> mechanism = new ObjectFactoryNode<>(Pkcs11MechanismObject.class, null);

        publicKey.addChild(Pkcs11KeyType.CKK_RSA.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11RsaPublicKeyObject.class, null));
        publicKey.addChild(Pkcs11KeyType.CKK_GOSTR3410.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11Gost256PublicKeyObject.class, null));
        // FIXME: Do not use Rt types here as Pkcs11ObjectFactory should register standard Pkcs11 classes, not
        //  RtPkcs11 ones (i.e. Pkcs11KeyType, not RtPkcs11KeyType)
        publicKey.addChild(RtPkcs11KeyType.CKK_GOSTR3410_512.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11Gost512PublicKeyObject.class, null));

        privateKey.addChild(Pkcs11KeyType.CKK_RSA.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11RsaPrivateKeyObject.class, null));
        privateKey.addChild(Pkcs11KeyType.CKK_GOSTR3410.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11Gost256PrivateKeyObject.class, null));
        privateKey.addChild(RtPkcs11KeyType.CKK_GOSTR3410_512.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11Gost512PrivateKeyObject.class, null));

        certificate.addChild(Pkcs11CertificateType.CKC_X_509.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11X509PublicKeyCertificateObject.class, null));
        certificate.addChild(Pkcs11CertificateType.CKC_WTLS.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11WtlsCertificateObject.class, null));
        certificate.addChild(Pkcs11CertificateType.CKC_X_509_ATTR_CERT.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11X509AttributeCertificateObject.class, null));

        hwFeature.addChild(Pkcs11HardwareFeatureType.CKH_MONOTONIC_COUNTER.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11MonotonicCounterObject.class, null));
        hwFeature.addChild(Pkcs11HardwareFeatureType.CKH_CLOCK.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11ClockObject.class, null));
        hwFeature.addChild(Pkcs11HardwareFeatureType.CKH_USER_INTERFACE.getAsLong(),
                new ObjectFactoryNode<>(Pkcs11UserInterfaceObject.class, null));

        mRootNode.addChild(Pkcs11ObjectClass.CKO_DATA.getAsLong(), data);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_CERTIFICATE.getAsLong(), certificate);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_PUBLIC_KEY.getAsLong(), publicKey);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_PRIVATE_KEY.getAsLong(), privateKey);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_SECRET_KEY.getAsLong(), secretKey);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_HW_FEATURE.getAsLong(), hwFeature);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_DOMAIN_PARAMETERS.getAsLong(), domainParameters);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_MECHANISM.getAsLong(), mechanism);
    }
}
