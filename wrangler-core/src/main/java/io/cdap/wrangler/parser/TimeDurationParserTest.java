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

import org.junit.Assert;
import org.junit.Test;

public class TimeDurationParserTest {

    @Test
    public void testTimeDurations() {
        Assert.assertEquals(1000, TimeDurationParser.parseToMillis("1s"));
        Assert.assertEquals(60000, TimeDurationParser.parseToMillis("1min"));
        Assert.assertEquals(3600000, TimeDurationParser.parseToMillis("1hr"));
        Assert.assertEquals(86400000, TimeDurationParser.parseToMillis("1 day"));
    }
}
