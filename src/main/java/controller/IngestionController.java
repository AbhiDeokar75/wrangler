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
package com.example.wrangler.controller;

import com.example.wrangler.model.IngestionRequest;
import com.example.wrangler.service.ClickHouseService;
import com.example.wrangler.service.FlatFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Handles ingestion-related HTTP requests.
 */

@RestController
@RequestMapping("/api")
@CrossOrigin
public class IngestionController {

    ClickHouseService chService = new ClickHouseService();
    FlatFileService fileService = new FlatFileService();

    @PostMapping("/ingest")
    public String ingest(@RequestBody IngestionRequest req) {
        try {
            var conn = chService.connectClickHouse(req);

            if (req.sourceType.equals("ClickHouseToFile")) {
                int count = chService.exportToCSV(conn, req);
                return "Exported " + count + " records to file.";
            } else if (req.sourceType.equals("FileToClickHouse")) {
                int count = fileService.importToClickHouse(conn, req);
                return "Imported " + count + " records to ClickHouse.";
            } else {
                return "Unknown sourceType";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
