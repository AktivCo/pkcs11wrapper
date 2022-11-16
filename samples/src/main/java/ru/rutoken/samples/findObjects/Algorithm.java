package ru.rutoken.samples.findObjects;

abstract class Algorithm {
    static final class GostAlgorithm2001 extends Algorithm {
        @Override
        public String toString() {
            return "GOST R 34.10-2001";
        }
    }

    static final class GostAlgorithm2012_256 extends Algorithm {
        @Override
        public String toString() {
            return "GOST R 34.10-2012 256 bit";
        }
    }

    static final class GostAlgorithm2012_512 extends Algorithm {
        @Override
        public String toString() {
            return "GOST R 34.10-2012 512 bit";
        }
    }

    static final class EcdsaAlgorithm extends Algorithm {
        @Override
        public String toString() {
            return "ECDSA";
        }
    }

    static final class RsaAlgorithm extends Algorithm {
        private final long mModulusBits;

        public RsaAlgorithm(long modulusBits) {
            mModulusBits = modulusBits;
        }

        public long getModulusBits() {
            return mModulusBits;
        }

        @Override
        public String toString() {
            return "RSA " + mModulusBits;
        }
    }
}
