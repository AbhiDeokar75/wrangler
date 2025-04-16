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
package com.example.wrangler.service;

import com.example.wrangler.model.IngestionRequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
/**
 * Service to handle Flat File-related operations.
 */
public class FlatFileService {

    public int importToClickHouse(Connection conn, IngestionRequest req) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(req.filePath));
        String line;
        int count = 0;

        Statement stmt = conn.createStatement();

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(req.delimiter);
            String insertSQL = "INSERT INTO " + req.tableName + " VALUES ('" + String.join("','", values) + "')";
            stmt.execute(insertSQL);
            count++;
        }

        return count;
    }
}
