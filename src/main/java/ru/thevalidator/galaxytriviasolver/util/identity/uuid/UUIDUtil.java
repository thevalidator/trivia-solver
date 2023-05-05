/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.util.identity.uuid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static ru.thevalidator.galaxytriviasolver.util.identity.os.OSValidator.*;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class UUIDUtil {

    private static final String UUID_PATTERN = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    public static String getUUID() throws IOException, InterruptedException {
        String uuid = null;
        ProcessBuilder builder = new ProcessBuilder();
        String command;
        if (IS_WINDOWS) {
            command = "wmic path win32_computersystemproduct get uuid";
            builder.command("cmd.exe", "/c", command);
        } else if (IS_UNIX) {
            command = "ls -la /dev/disk/by-uuid/ | grep -E \"" + UUID_PATTERN + "\"";
            builder.command("sh", "-c", command);
        } else if (IS_MAC) {
            command = "ioreg -d2 -c IOPlatformExpertDevice | awk -F\\\" '/IOPlatformUUID/{print $(NF-1)}'";
            builder.command("sh", "-c", command);
        } else {
            throw new UnsupportedOperationException("Unsupported operating system");
        }

        Process proc = builder.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (IS_UNIX) {
                    String[] parts = line.split("(\\s){1,}");
                    for (String p: parts) {
                        if (p.length() == 36 && p.matches(UUID_PATTERN)) {
                            uuid = p;
                            break;
                        }
                    }
                } else {
                    line = line.trim();
                    if (line.matches(UUID_PATTERN)) {
                        uuid = line;
                        break;
                    }
                }

            }
        }

        proc.waitFor();
        proc.destroy();

        return uuid;
    }

}
