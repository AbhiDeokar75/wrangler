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

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDurationParser {

    private static final Pattern PATTERN = Pattern.compile("(\\d+(\\.\\d+)?)(\\s*)([a-zA-Z]+)");

    public static long parseToMillis(String input) {
        Matcher matcher = PATTERN.matcher(input.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid time duration: " + input);
        }

        double value = Double.parseDouble(matcher.group(1));
        String unit = matcher.group(4).toLowerCase();

        switch (unit) {
            case "ms":
            case "millisecond":
            case "milliseconds":
                return (long) value;
            case "s":
            case "sec":
            case "second":
            case "seconds":
                return (long) (value * 1000);
            case "m":
            case "min":
            case "minute":
            case "minutes":
                return (long) (value * 60 * 1000);
            case "h":
            case "hr":
            case "hour":
            case "hours":
                return (long) (value * 60 * 60 * 1000);
            case "d":
            case "day":
            case "days":
                return (long) (value * 24 * 60 * 60 * 1000);
            default:
                throw new IllegalArgumentException("Unknown time unit: " + unit);
        }
    }
}
