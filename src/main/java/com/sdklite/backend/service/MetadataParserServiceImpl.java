package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetadataParserServiceImpl implements MetadataParserService {

    @Override
    public List<CounterDef> parseCounters(Reader reader) throws IOException {
        List<CounterDef> counters = new ArrayList<>();
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        try (CSVParser parser = new CSVParser(reader, format)) {
            for (CSVRecord record : parser) {
                CounterDef def = new CounterDef();
                def.setId(record.isMapped("id") ? record.get("id") : null);
                def.setName(record.isMapped("name") ? record.get("name") : null);
                
                Map<String, String> attrs = new HashMap<>();
                record.toMap().forEach(attrs::put);
                def.setAttributes(attrs);
                
                counters.add(def);
            }
        }
        return counters;
    }

    @Override
    public List<MocDef> parseMocs(Reader reader) throws IOException {
        List<MocDef> mocs = new ArrayList<>();
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        try (CSVParser parser = new CSVParser(reader, format)) {
            for (CSVRecord record : parser) {
                MocDef def = new MocDef();
                def.setId(record.isMapped("idf_moc_def") ? record.get("idf_moc_def") : null);
                // Assuming mapping based on sample file: idf_moc_def1 might be parent or child logic, 
                // keeping it generic for now as per requirement to just parse.
                // relationships will be built in logic layer, here we just load raw data.
                
                Map<String, String> attrs = new HashMap<>();
                record.toMap().forEach(attrs::put);
                def.setAttributes(attrs);
                
                mocs.add(def);
            }
        }
        return mocs;
    }
}
