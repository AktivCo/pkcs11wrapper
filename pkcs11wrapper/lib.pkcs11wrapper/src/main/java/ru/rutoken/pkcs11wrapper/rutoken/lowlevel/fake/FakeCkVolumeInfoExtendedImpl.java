package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeInfoExtended;

class FakeCkVolumeInfoExtendedImpl implements CkVolumeInfoExtended {
    @Override
    public long getVolumeId() {
        return 0;
    }

    @Override
    public long getVolumeSize() {
        return 0;
    }

    @Override
    public long getAccessMode() {
        return 0;
    }

    @Override
    public long getVolumeOwner() {
        return 0;
    }

    @Override
    public long getFlags() {
        return 0;
    }
}
