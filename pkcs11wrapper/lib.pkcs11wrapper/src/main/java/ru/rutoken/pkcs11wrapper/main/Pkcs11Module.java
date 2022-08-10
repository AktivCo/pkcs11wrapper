package ru.rutoken.pkcs11wrapper.main;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.datatype.IPkcs11InitializeArgs;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11Info;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.LongArrayCallConvention;

/**
 * Adds default implementations for pkcs11 related methods of {@link IPkcs11Module} interface.
 */
public interface Pkcs11Module extends IPkcs11Module {
    @Override
    default Pkcs11Info getInfo() {
        return new Pkcs11Info(getApi().C_GetInfo());
    }

    @Override
    default List<Pkcs11Slot> getSlotList(boolean tokenPresent) {
        final byte tokenPresentFlag = tokenPresent ? Pkcs11Constants.CK_TRUE : Pkcs11Constants.CK_FALSE;
        final long[] slotIds = new LongArrayCallConvention("C_GetSlotList") {
            @Override
            protected int getLength() {
                final MutableLong count = new MutableLong();
                getApi().C_GetSlotList(tokenPresentFlag, null, count);
                return (int) count.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(long[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_GetSlotList(tokenPresentFlag, values, length));
            }
        }.call();

        final List<Pkcs11Slot> slots = new ArrayList<>();
        for (long id : slotIds) {
            slots.add(getHighLevelFactory().makeSlot(this, id));
        }
        return slots;
    }

    @Nullable
    @Override
    default Pkcs11Slot waitForSlotEvent(boolean dontBlock) {
        final long flags = dontBlock ? Pkcs11Flag.CKF_DONT_BLOCK.getAsLong() : 0L;
        final MutableLong id = new MutableLong();
        final IPkcs11ReturnValue res = IPkcs11ReturnValue.getInstance(
                getLowLevelApi().C_WaitForSlotEvent(flags, id, null), getVendorExtensions());
        if (dontBlock && res == Pkcs11ReturnValue.CKR_NO_EVENT)
            return null;
        Pkcs11Exception.throwIfNotOk(res, "C_WaitForSlotEvent failed");
        return getHighLevelFactory().makeSlot(this, id.value);
    }

    @Override
    default void initializeModule(@Nullable IPkcs11InitializeArgs arguments) {
        getApi().C_Initialize(arguments != null ? arguments.toCkCInitializeArgs(getLowLevelFactory()) : null);
    }

    @Override
    default void finalizeModule() {
        getApi().C_Finalize(null);
    }
}
