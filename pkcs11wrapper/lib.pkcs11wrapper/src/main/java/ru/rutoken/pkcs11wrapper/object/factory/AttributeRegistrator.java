package ru.rutoken.pkcs11wrapper.object.factory;

/**
 * Helper interface for attributes registration in objects.
 */
public interface AttributeRegistrator {
    /**
     * Adds subclass attributes into an attributes map.
     */
    void registerAttributes();
}
