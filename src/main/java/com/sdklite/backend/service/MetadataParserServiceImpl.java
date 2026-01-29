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

    private Integer parseInteger(String val) {
        if (val == null || val.isEmpty()) return null;
        try { return Integer.parseInt(val); } catch (NumberFormatException e) { return null; }
    }

    private Long parseLong(String val) {
        if (val == null || val.isEmpty()) return null;
        try { return Long.parseLong(val); } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<CounterDef> parseCounters(Reader reader) throws IOException {
        List<CounterDef> counters = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                CounterDef def = new CounterDef();
                def.setId(record.isMapped("id") ? record.get("id") : null);
                def.setName(record.isMapped("name") ? record.get("name") : null);
                def.setFormula(record.isMapped("formula") ? record.get("formula") : null);
                def.setAggregateType(record.isMapped("aggregate_type") ? parseInteger(record.get("aggregate_type")) : null);
                def.setIntegralType(record.isMapped("integral_type") ? parseInteger(record.get("integral_type")) : null);
                def.setRsComment(record.isMapped("rs_comment") ? record.get("rs_comment") : null);
                
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
                def.setId(record.isMapped("id") ? record.get("id") : null);
                def.setName(record.isMapped("name") ? record.get("name") : null);
                def.setBehaviour(record.isMapped("behaviour") ? parseInteger(record.get("behaviour")) : null);
                def.setFlags(record.isMapped("flags") ? parseInteger(record.get("flags")) : null);
                def.setIconFilename(record.isMapped("icon_filename") ? record.get("icon_filename") : null);
                def.setRsComment(record.isMapped("rs_comment") ? record.get("rs_comment") : null);
                
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
                item.setImportPriority(record.isMapped("import_priority") ? parseInteger(record.get("import_priority")) : null);
                item.setLastImportDate(record.isMapped("last_import_date") ? record.get("last_import_date") : null);
                item.setLastImportLineCount(record.isMapped("last_import_line_count") ? parseLong(record.get("last_import_line_count")) : null);
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
                item.setBehaviour(record.isMapped("behaviour") ? parseInteger(record.get("behaviour")) : null);
                item.setIconFilename(record.isMapped("icon_filename") ? record.get("icon_filename") : null);
                item.setRsComment(record.isMapped("rs_comment") ? record.get("rs_comment") : null);
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<MocAttributeDef> parseAttributes(Reader reader) throws IOException {
        List<MocAttributeDef> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                MocAttributeDef item = new MocAttributeDef();
                item.setId(record.isMapped("id") ? record.get("id") : null);
                item.setMappedAttributeId(record.isMapped("idf_mapped_attribute") ? record.get("idf_mapped_attribute") : null);
                item.setBehaviour(record.isMapped("behaviour") ? parseInteger(record.get("behaviour")) : null);
                item.setFlags(record.isMapped("flags") ? parseInteger(record.get("flags")) : null);
                item.setIntegralType(record.isMapped("integral_type") ? parseInteger(record.get("integral_type")) : null);
                item.setName(record.isMapped("name") ? record.get("name") : null);
                item.setRefGsm(record.isMapped("ref_gsm") ? record.get("ref_gsm") : null);
                item.setRsComment(record.isMapped("rs_comment") ? record.get("rs_comment") : null);
                
                Map<String, String> attrs = new HashMap<>();
                record.toMap().forEach(attrs::put);
                item.setAttributes(attrs);
                
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<ImportAttrFor> parseAttributeMappings(Reader reader) throws IOException {
        List<ImportAttrFor> list = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, getFormat())) {
            for (CSVRecord record : parser) {
                ImportAttrFor item = new ImportAttrFor();
                item.setAttrImportId(record.isMapped("idf_attr_import") ? record.get("idf_attr_import") : null);
                item.setVendorMocDefId(record.isMapped("idf_vendor_moc_def") ? record.get("idf_vendor_moc_def") : null);
                list.add(item);
            }
        }
        return list;
    }
}