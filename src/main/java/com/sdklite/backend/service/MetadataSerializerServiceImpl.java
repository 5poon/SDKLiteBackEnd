package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
import com.sdklite.backend.model.MocAttributeDef;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MetadataSerializerServiceImpl implements MetadataSerializerService {

    private CSVFormat getFormat(Set<String> headers) {
        return CSVFormat.DEFAULT.builder()
                .setHeader(headers.toArray(new String[0]))
                .setQuoteMode(org.apache.commons.csv.QuoteMode.ALL_NON_NULL)
                .build();
    }

    private String toString(Object val) {
        return val == null ? "" : val.toString();
    }

    @Override
    public void serializeCounters(List<CounterDef> counters, Writer writer) throws IOException {
        if (counters == null || counters.isEmpty()) {
            return;
        }

        Set<String> headers = counters.get(0).getAttributes().keySet();
        CSVFormat format = getFormat(headers);

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (CounterDef counter : counters) {
                Map<String, String> attrs = counter.getAttributes();
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    if ("id".equalsIgnoreCase(header)) {
                        values.add(counter.getId());
                    } else if ("name".equalsIgnoreCase(header)) {
                        values.add(counter.getName());
                    } else if ("formula".equalsIgnoreCase(header)) {
                        values.add(counter.getFormula());
                    } else if ("aggregate_type".equalsIgnoreCase(header)) {
                        values.add(toString(counter.getAggregateType()));
                    } else if ("integral_type".equalsIgnoreCase(header)) {
                        values.add(toString(counter.getIntegralType()));
                    } else if ("rs_comment".equalsIgnoreCase(header)) {
                        values.add(counter.getRsComment());
                    } else {
                        values.add(attrs.get(header));
                    }
                }
                printer.printRecord(values);
            }
        }
    }

    @Override
    public void serializeMocs(List<MocDef> mocs, Writer writer) throws IOException {
        if (mocs == null || mocs.isEmpty()) {
            return;
        }

        Set<String> headers = mocs.get(0).getAttributes().keySet();
        CSVFormat format = getFormat(headers);

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (MocDef moc : mocs) {
                Map<String, String> attrs = moc.getAttributes();
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    if ("id".equalsIgnoreCase(header)) {
                        values.add(moc.getId());
                    } else if ("name".equalsIgnoreCase(header)) {
                        values.add(moc.getName());
                    } else if ("behaviour".equalsIgnoreCase(header)) {
                        values.add(toString(moc.getBehaviour()));
                    } else if ("flags".equalsIgnoreCase(header)) {
                        values.add(toString(moc.getFlags()));
                    } else if ("icon_filename".equalsIgnoreCase(header)) {
                        values.add(moc.getIconFilename());
                    } else if ("rs_comment".equalsIgnoreCase(header)) {
                        values.add(moc.getRsComment());
                    } else {
                        values.add(attrs.get(header));
                    }
                }
                printer.printRecord(values);
            }
        }
    }

    @Override
    public void serializeAttributes(List<MocAttributeDef> attributes, Writer writer) throws IOException {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }

        Set<String> headers = attributes.get(0).getAttributes().keySet();
        CSVFormat format = getFormat(headers);

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (MocAttributeDef attr : attributes) {
                Map<String, String> attrs = attr.getAttributes();
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    if ("id".equalsIgnoreCase(header)) {
                        values.add(attr.getId());
                    } else if ("name".equalsIgnoreCase(header)) {
                        values.add(attr.getName());
                    } else if ("idf_mapped_attribute".equalsIgnoreCase(header)) {
                        values.add(attr.getMappedAttributeId());
                    } else if ("behaviour".equalsIgnoreCase(header)) {
                        values.add(toString(attr.getBehaviour()));
                    } else if ("flags".equalsIgnoreCase(header)) {
                        values.add(toString(attr.getFlags()));
                    } else if ("integral_type".equalsIgnoreCase(header)) {
                        values.add(toString(attr.getIntegralType()));
                    } else if ("ref_gsm".equalsIgnoreCase(header)) {
                        values.add(attr.getRefGsm());
                    } else if ("rs_comment".equalsIgnoreCase(header)) {
                        values.add(attr.getRsComment());
                    } else {
                        values.add(attrs.get(header));
                    }
                }
                printer.printRecord(values);
            }
        }
    }
}
