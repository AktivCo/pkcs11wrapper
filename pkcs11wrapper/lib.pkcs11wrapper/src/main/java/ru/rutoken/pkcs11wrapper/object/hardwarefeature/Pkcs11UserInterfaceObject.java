package ru.rutoken.pkcs11wrapper.object.hardwarefeature;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_BITS_PER_PIXEL;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CHAR_COLUMNS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CHAR_ROWS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CHAR_SETS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_COLOR;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ENCODING_METHODS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_MIME_TYPES;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PIXEL_X;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PIXEL_Y;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_RESOLUTION;

public class Pkcs11UserInterfaceObject extends Pkcs11HardwareFeatureObject {
    private final Pkcs11LongAttribute mPixelXAttribute = new Pkcs11LongAttribute(CKA_PIXEL_X);
    private final Pkcs11LongAttribute mPixelYAttribute = new Pkcs11LongAttribute(CKA_PIXEL_Y);
    private final Pkcs11LongAttribute mResolutionAttribute = new Pkcs11LongAttribute(CKA_RESOLUTION);
    private final Pkcs11LongAttribute mCharRowsAttribute = new Pkcs11LongAttribute(CKA_CHAR_ROWS);
    private final Pkcs11LongAttribute mCharColumnsAttribute = new Pkcs11LongAttribute(CKA_CHAR_COLUMNS);
    private final Pkcs11BooleanAttribute mColorAttribute = new Pkcs11BooleanAttribute(CKA_COLOR);
    private final Pkcs11LongAttribute mBitsPerPixelAttribute = new Pkcs11LongAttribute(CKA_BITS_PER_PIXEL);
    private final Pkcs11StringAttribute mCharSetsAttribute = new Pkcs11StringAttribute(CKA_CHAR_SETS);
    private final Pkcs11StringAttribute mEncodingMethodsAttribute = new Pkcs11StringAttribute(CKA_ENCODING_METHODS);
    private final Pkcs11StringAttribute mMimeTypesAttribute = new Pkcs11StringAttribute(CKA_MIME_TYPES);

    protected Pkcs11UserInterfaceObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11LongAttribute getPixelYAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPixelYAttribute);
    }

    public Pkcs11LongAttribute getResolutionAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mResolutionAttribute);
    }

    public Pkcs11LongAttribute getCharRowsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCharRowsAttribute);
    }

    public Pkcs11LongAttribute getCharColumnsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCharColumnsAttribute);
    }

    public Pkcs11BooleanAttribute getColorAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mColorAttribute);
    }

    public Pkcs11LongAttribute getBitsPerPixelAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mBitsPerPixelAttribute);
    }

    public Pkcs11StringAttribute getCharSetsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCharSetsAttribute);
    }

    public Pkcs11StringAttribute getEncodingMethodsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEncodingMethodsAttribute);
    }

    public Pkcs11StringAttribute getMimeTypesAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mMimeTypesAttribute);
    }

    public Pkcs11LongAttribute getPixelXAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPixelXAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mPixelXAttribute);
        registerAttribute(mPixelYAttribute);
        registerAttribute(mResolutionAttribute);
        registerAttribute(mCharRowsAttribute);
        registerAttribute(mCharColumnsAttribute);
        registerAttribute(mColorAttribute);
        registerAttribute(mBitsPerPixelAttribute);
        registerAttribute(mCharSetsAttribute);
        registerAttribute(mEncodingMethodsAttribute);
        registerAttribute(mMimeTypesAttribute);
    }
}


