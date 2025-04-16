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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to handle ClickHouse-related operations.
 */
public class ClickHouseService {

    public Connection connectClickHouse(IngestionRequest req) throws Exception {
        String url = "jdbc:clickhouse://" + req.host + ":" + req.port + "/" + req.database;
        Connection conn = DriverManager.getConnection(url, req.user, req.jwtToken);
        return conn;
    }

    public List<String> fetchTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SHOW TABLES");
        while (rs.next()) {
            tables.add(rs.getString(1));
        }
        return tables;
    }

    public List<String> fetchColumns(Connection conn, String table) throws SQLException {
        List<String> columns = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("DESCRIBE TABLE " + table);
        while (rs.next()) {
            columns.add(rs.getString(1));
        }
        return columns;
    }

    public int exportToCSV(Connection conn, IngestionRequest req) throws Exception {
        String columnStr = String.join(",", req.selectedColumns);
        String query = "SELECT " + columnStr + " FROM " + req.tableName;
        ResultSet rs = conn.createStatement().executeQuery(query);

        java.io.FileWriter writer = new java.io.FileWriter(req.filePath);
        int count = 0;
        while (rs.next()) {
            for (int i = 1; i <= req.selectedColumns.size(); i++) {
                writer.append(rs.getString(i));
                if (i < req.selectedColumns.size()) {
                    writer.append(req.delimiter);
                }
            }
            writer.append("\n");
            count++;
        }
        writer.flush();
        writer.close();
        return count;
    }
}

