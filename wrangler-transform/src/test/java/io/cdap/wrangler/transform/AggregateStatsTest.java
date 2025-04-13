/*
 * Copyright © 2025 Cask Data, Inc.
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
 * License for the specific language governing permissions and limitations under the
 * License.
 */

package io.cdap.wrangler.transform;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Text;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AggregateStatsTest {

  @Test
  public void testAverageAggregation() throws Exception {
    List<Row> input = Arrays.asList(
        new Row().add("group", "A").add("value", 10),
        new Row().add("group", "A").add("value", 20),
        new Row().add("group", "B").add("value", 30));

    AggregateStats directive = new AggregateStats(
        new Text("avg"),
        new ColumnName("group"),
        new ColumnName("value"));

    List<Row> result = directive.execute(input);

    assertEquals(2, result.size());

    for (Row row : result) {
      String group = (String) row.getValue("group");
      double avg = (Double) row.getValue("avg_value");

      if ("A".equals(group)) {
        assertEquals(15.0, avg, 0.0001);
      } else if ("B".equals(group)) {
        assertEquals(30.0, avg, 0.0001);
      } else {
        // Unexpected group
        assertTrue("Unexpected group: " + group, false);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsupportedAggregationType() throws Exception {
    new AggregateStats(
        new Text("sum"),
        new ColumnName("group"),
        new ColumnName("value")).execute(Arrays.asList());
  }

  @Test(expected = RuntimeException.class)
  public void testNonNumericValueColumn() throws Exception {
    List<Row> input = Arrays.asList(
        new Row().add("group", "A").add("value", "non-numeric"));

    AggregateStats directive = new AggregateStats(
        new Text("avg"),
        new ColumnName("group"),
        new ColumnName("value"));

    directive.execute(input);
  }
}
