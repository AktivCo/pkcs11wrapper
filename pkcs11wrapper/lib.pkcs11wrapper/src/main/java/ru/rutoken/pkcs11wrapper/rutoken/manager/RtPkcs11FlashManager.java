/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.manager;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Token;
import ru.rutoken.pkcs11wrapper.manager.impl.BaseTokenManager;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VolumeFormatInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VolumeInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeFormatInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Api;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public class RtPkcs11FlashManager extends BaseTokenManager {
    public RtPkcs11FlashManager(Pkcs11Token token) {
        super(token);
    }

    private static List<VolumeInfoExtended> toVolumeInfoExtendedList(CkVolumeInfoExtended[] ckVolumeInfoExtendedArray) {
        final List<VolumeInfoExtended> result = new ArrayList<>(ckVolumeInfoExtendedArray.length);
        for (CkVolumeInfoExtended ckVolumeInfoExtended : ckVolumeInfoExtendedArray) {
            result.add(new VolumeInfoExtended(ckVolumeInfoExtended));
        }
        return result;
    }

    public List<VolumeInfoExtended> getVolumesInfo() {
        MutableLong mutableLength = new MutableLong();
        getApi().C_EX_GetVolumesInfo(getToken().getSlot().getId(), null, mutableLength);
        final int expectedLength = (int) mutableLength.value;

        if (expectedLength == 0)
            return emptyList();
        else if (expectedLength < 0)
            throw new IllegalStateException("Length of volumes info is less than zero");

        CkVolumeInfoExtended[] lowLevelInfo = makeCkVolumeInfoExtendedArray(expectedLength);
        getApi().C_EX_GetVolumesInfo(getToken().getSlot().getId(), lowLevelInfo, mutableLength);

        // Result may be shorter that was expected because pkcs convention is allowed to initially return a longer
        // length than is actually required
        if (mutableLength.value < lowLevelInfo.length)
            lowLevelInfo = Arrays.copyOf(lowLevelInfo, (int) mutableLength.value);

        return toVolumeInfoExtendedList(lowLevelInfo);
    }

    public long getDriveSize() {
        return getApi().C_EX_GetDriveSize(getSlot().getId());
    }

    public void changeVolumeAttributes(long userType, String pin, long volumeId, AccessMode newAccessMode,
                                       boolean permanent) {
        getApi().C_EX_ChangeVolumeAttributes(getSlot().getId(), userType, pin.getBytes(), volumeId,
                newAccessMode.getAsLong(), permanent);
    }

    public void formatDrive(long userType, String pin, List<VolumeFormatInfoExtended> formatParams) {
        getApi().C_EX_FormatDrive(getSlot().getId(), userType, pin.getBytes(),
                toCkVolumeFormatInfoExtendedList(formatParams));
    }

    @Override
    public RtPkcs11Api getApi() {
        return (RtPkcs11Api) super.getApi();
    }

    @Override
    public IRtPkcs11LowLevelApi getLowLevelApi() {
        return (IRtPkcs11LowLevelApi) super.getLowLevelApi();
    }

    @Override
    public IRtPkcs11LowLevelFactory getLowLevelFactory() {
        return (IRtPkcs11LowLevelFactory) super.getLowLevelFactory();
    }

    @Override
    public RtPkcs11Token getToken() {
        return (RtPkcs11Token) mToken;
    }

    private CkVolumeInfoExtended[] makeCkVolumeInfoExtendedArray(int size) {
        final CkVolumeInfoExtended[] result = new CkVolumeInfoExtended[size];
        for (int i = 0; i < size; i++) {
            // We cannot set fields of this object as its datatype interface has no setters (it's a read-only structure)
            result[i] = getLowLevelFactory().makeVolumeInfoExtended();
        }
        return result;
    }

    private List<CkVolumeFormatInfoExtended> toCkVolumeFormatInfoExtendedList(
            List<VolumeFormatInfoExtended> formatInfo) {
        final List<CkVolumeFormatInfoExtended> result = new ArrayList<>(formatInfo.size());
        for (VolumeFormatInfoExtended info : formatInfo) {
            result.add(info.toCkVolumeFormatInfoExtended(getLowLevelFactory()));
        }
        return result;
    }

    public enum AccessMode implements LongValueSupplier {
        ACCESS_MODE_HIDDEN(0x00000000L),
        ACCESS_MODE_RO(0x00000001L),
        ACCESS_MODE_RW(0x00000003L),
        ACCESS_MODE_CD(0x00000005L);

        private static final EnumFromValueHelper<AccessMode> FROM_VALUE_HELPER = new EnumFromValueHelper<>();

        private final long mValue;

        AccessMode(long value) {
            mValue = value;
        }

        public static AccessMode fromValue(long value) {
            return FROM_VALUE_HELPER.fromValue(value, AccessMode.class);
        }

        @Override
        public long getAsLong() {
            return mValue;
        }
    }
}
