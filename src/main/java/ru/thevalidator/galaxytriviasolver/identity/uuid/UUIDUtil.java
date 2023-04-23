/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.identity.uuid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ru.thevalidator.galaxytriviasolver.identity.os.OSValidator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class UUIDUtil {

    private static final String UUID_PATTERN = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    public static String getUUID() throws IOException, InterruptedException {
        String uuid = null;
        if (OSValidator.IS_WINDOWS) {
            uuid = UUIDUtil.getWindowsUUID();
        } else if (OSValidator.IS_UNIX) {

        } else if (OSValidator.IS_MAC) {

        } else {
            throw new UnsupportedOperationException("Unsupported operating system");
        }

        return uuid;
    }

    private static String getWindowsUUID() throws IOException, InterruptedException {
        String uuid = null;
        Process proc = Runtime.getRuntime().exec("wmic path win32_computersystemproduct get uuid");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches(UUID_PATTERN)) {
                    uuid = line;
                }
            }
        }
        proc.waitFor();
        proc.destroy();

        return uuid;
    }

    private static String getUnixUUID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static String getMacUUID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
