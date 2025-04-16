/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.wrangler.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteSizeParser {

    private static final Pattern PATTERN = Pattern.compile("(\\d+(\\.\\d+)?)(\\s*)([a-zA-Z]+)");

    public static long parse(String input) {
        Matcher matcher = PATTERN.matcher(input.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid byte size: " + input);
        }

        double value = Double.parseDouble(matcher.group(1));
        String unit = matcher.group(4).toLowerCase();

        switch (unit) {
            case "b":
                return (long) value;
            case "kb":
                return (long) (value * 1_000);
            case "mb":
                return (long) (value * 1_000_000);
            case "gb":
                return (long) (value * 1_000_000_000);
            case "tb":
                return (long) (value * 1_000_000_000_000L);
            case "kib":
                return (long) (value * 1024);
            case "mib":
                return (long) (value * 1024 * 1024);
            case "gib":
                return (long) (value * 1024 * 1024 * 1024);
            case "tib":
                return (long) (value * 1024L * 1024 * 1024 * 1024);
            default:
                throw new IllegalArgumentException("Unknown byte unit: " + unit);
        }
    }
}
