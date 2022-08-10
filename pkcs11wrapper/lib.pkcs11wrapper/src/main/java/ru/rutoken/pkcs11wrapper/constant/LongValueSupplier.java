package ru.rutoken.pkcs11wrapper.constant;

/**
 * Interface for getting value of pkcs11 constant represented with enum.
 */
@FunctionalInterface
public interface LongValueSupplier {
    long getAsLong();
}
