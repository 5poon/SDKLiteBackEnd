package com.sdklite.backend.service;

import com.sdklite.backend.model.*;
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

    private CSVFormat getFormat() {
        return CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();
    }

    @Override
    public List<CounterDef> parseCounters(Reader reader) throws IOException {
        List<CounterDef> counters = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
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
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                MocDef def = new MocDef();
                def.setId(record.isMapped("idf_moc_def") ? record.get("idf_moc_def") : null);
                
                Map<String, String> attrs = new HashMap<>();
                record.toMap().forEach(attrs::put);
                def.setAttributes(attrs);
                
                mocs.add(def);
            }
        }
        return mocs;
    }

    @Override
    public List<ImportDataSource> parseDataSources(Reader reader) throws IOException {
        List<ImportDataSource> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                ImportDataSource item = new ImportDataSource();
                item.setId(record.isMapped("id") ? record.get("id") : null);
                item.setName(record.isMapped("name") ? record.get("name") : null);
                item.setBeanClass(record.isMapped("bean_class") ? record.get("bean_class") : null);
                item.setConnectionString(record.isMapped("connection_string") ? record.get("connection_string") : null);
                item.setActive(record.isMapped("is_active") && "1".equals(record.get("is_active")));
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<NeImportEntity> parseNeEntities(Reader reader) throws IOException {
        List<NeImportEntity> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                NeImportEntity item = new NeImportEntity();
                item.setId(record.isMapped("id") ? record.get("id") : null);
                item.setDataSourceId(record.isMapped("idf_import_datasource") ? record.get("idf_import_datasource") : null);
                item.setEntityName(record.isMapped("entity_name") ? record.get("entity_name") : null);
                item.setConnectionString(record.isMapped("connection_string") ? record.get("connection_string") : null);
                item.setActive(record.isMapped("is_active") && "1".equals(record.get("is_active")));
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<CounterImportEntity> parseCounterEntities(Reader reader) throws IOException {
        List<CounterImportEntity> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                CounterImportEntity item = new CounterImportEntity();
                item.setId(record.isMapped("id") ? record.get("id") : null);
                item.setDataSourceId(record.isMapped("idf_import_datasource") ? record.get("idf_import_datasource") : null);
                item.setNeListSelectorId(record.isMapped("idf_ne_list_selector") ? record.get("idf_ne_list_selector") : null);
                item.setEntityName(record.isMapped("entity_name") ? record.get("entity_name") : null);
                item.setConnectionString(record.isMapped("connection_string") ? record.get("connection_string") : null);
                item.setActive(record.isMapped("is_active") && "1".equals(record.get("is_active")));
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<AttrImportEntity> parseAttrEntities(Reader reader) throws IOException {
        List<AttrImportEntity> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                AttrImportEntity item = new AttrImportEntity();
                item.setId(record.isMapped("id") ? record.get("id") : null);
                item.setDataSourceId(record.isMapped("idf_import_datasource") ? record.get("idf_import_datasource") : null);
                item.setEntityName(record.isMapped("entity_name") ? record.get("entity_name") : null);
                item.setConnectionString(record.isMapped("connection_string") ? record.get("connection_string") : null);
                item.setActive(record.isMapped("is_active") && "1".equals(record.get("is_active")));
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<MocDefParent> parseMocParents(Reader reader) throws IOException {
        List<MocDefParent> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                MocDefParent item = new MocDefParent();
                item.setChildId(record.isMapped("idf_moc_def") ? record.get("idf_moc_def") : null);
                item.setParentId(record.isMapped("idf_moc_def1") ? record.get("idf_moc_def1") : null);
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<VendorMocDef> parseVendorMocs(Reader reader) throws IOException {
        List<VendorMocDef> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                VendorMocDef item = new VendorMocDef();
                item.setId(record.isMapped("id") ? record.get("id") : null);
                item.setMocDefId(record.isMapped("idf_moc_def") ? record.get("idf_moc_def") : null);
                item.setVendorId(record.isMapped("idf_vendor") ? record.get("idf_vendor") : null);
                item.setName(record.isMapped("name") ? record.get("name") : null);
                list.add(item);
            }
        }
        return list;
    }
}
