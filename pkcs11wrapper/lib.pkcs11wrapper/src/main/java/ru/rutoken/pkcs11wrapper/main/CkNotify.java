package ru.rutoken.pkcs11wrapper.main;

/**
 * Callback interface, called by pkcs11 library.
 */
public interface CkNotify {
    void onEvent(long session, long event, Object application);
}
