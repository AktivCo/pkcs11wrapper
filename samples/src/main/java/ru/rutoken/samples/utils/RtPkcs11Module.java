/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.utils;

import com.sun.jna.Native;
import com.sun.jna.Platform;

import ru.rutoken.pkcs11jna.RtPkcs11;
import ru.rutoken.pkcs11wrapper.main.Pkcs11BaseModule;
import ru.rutoken.pkcs11wrapper.rutoken.attribute.RtPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Api;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11HighLevelFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static ru.rutoken.samples.utils.Utils.TODO;

public final class RtPkcs11Module extends Pkcs11BaseModule {
    private static String PKCS11_LIBRARY_NAME = "rtpkcs11ecp";
    private static RtPkcs11Module INSTANCE = null;

    private RtPkcs11Module() {
        super(new RtPkcs11Api(new RtPkcs11JnaLowLevelApi(
                        Native.load(PKCS11_LIBRARY_NAME, RtPkcs11.class),
                        new RtPkcs11JnaLowLevelFactory()
                )),
                new RtPkcs11HighLevelFactory(),
                new RtPkcs11AttributeFactory()
        );
    }

    public static RtPkcs11Module getInstance(String[] args) {
        if (INSTANCE != null) return INSTANCE;

        if (Platform.isMac()) {
            try {
//                final String pkcs11FrameworkPath = Paths.get(System.getProperty("user.dir"),
//                        "external/pkcs11ecp/osx-x86_64+arm64/rtpkcs11ecp.framework").toString();
                final String pkcs11FrameworkPath = args.length > 0 ? args[0] :
                        TODO("Insert full path to the rtpkcs11ecp framework");

                PKCS11_LIBRARY_NAME = copyPkcs11FrameworkToResources(pkcs11FrameworkPath);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
//            final String pkcs11LibrarySearchPath = Paths.get(System.getProperty("user.dir"),
//                    "external/pkcs11ecp/glibc-x86_64/lib").toString(); // Linux
//            final String pkcs11LibrarySearchPath = Paths.get(System.getProperty("user.dir"),
//                    "external/pkcs11ecp/windows-x86_64/lib").toString(); // Windows

            final String pkcs11LibrarySearchPath = args.length > 0 ? args[0] :
                    TODO("Insert full path to the directory containing rtpkcs11ecp library");
            System.setProperty("jna.library.path", pkcs11LibrarySearchPath);
        }

        INSTANCE = new RtPkcs11Module();
        return INSTANCE;
    }

    /**
     * Copies pkcs11ecp.framework from sourcePath directory to resources.
     *
     * @return Absolute path of pkcs11ecp library in resources directory. This path can be used in Native.load function.
     */
    private static String copyPkcs11FrameworkToResources(String sourcePath) throws IOException {
        String versionPath = null;
        try (final var stream = Files.newDirectoryStream(Paths.get(sourcePath, "Versions"))) {
            for (final var path : stream) {
                if (Files.isDirectory(path) && !path.getFileName().toString().equals("Current")) {
                    versionPath = path.toAbsolutePath().toString();
                    break;
                }
            }
        }

        if (versionPath == null)
            throw new IllegalStateException("Cannot find path inside framework");

        return copyPkcs11Framework(Paths.get(versionPath),
                Paths.get(System.getProperty("user.dir"), "src/main/resources/darwin"));
    }

    /**
     * Copies pkcs11ecp.framework from sourcePath directory to destinationPath directory.
     *
     * @return Absolute path of pkcs11ecp library in destinationPath directory.
     */
    private static String copyPkcs11Framework(Path sourcePath, Path destinationPath) throws IOException {
        final var pkcs11Path = new String[]{""};
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                final var currentTarget = destinationPath.resolve(sourcePath.relativize(dir).toString());
                Files.createDirectories(currentTarget);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                final var currentTarget = destinationPath.resolve(sourcePath.relativize(file).toString());
                Files.copy(file, currentTarget, StandardCopyOption.REPLACE_EXISTING);
                if (file.getFileName().toString().equals("rtpkcs11ecp"))
                    pkcs11Path[0] = currentTarget.toString();
                return FileVisitResult.CONTINUE;
            }
        });

        return pkcs11Path[0];
    }
}
