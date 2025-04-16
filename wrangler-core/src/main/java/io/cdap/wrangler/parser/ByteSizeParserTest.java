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

public class ByteSizeParserTest {

    @Test
    public void testByteSizes() {
        Assert.assertEquals(1024, ByteSizeParser.parse("1KiB"));
        Assert.assertEquals(1_000_000, ByteSizeParser.parse("1MB"));
        Assert.assertEquals(1_073_741_824, ByteSizeParser.parse("1GiB"));
        Assert.assertEquals(500, ByteSizeParser.parse("500B"));
    }
}
