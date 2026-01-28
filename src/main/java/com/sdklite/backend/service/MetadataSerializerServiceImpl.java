package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
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

    @Override
    public void serializeCounters(List<CounterDef> counters, Writer writer) throws IOException {
        if (counters == null || counters.isEmpty()) {
            return;
        }

        // Use the attributes map keys from the first record as the canonical header order
        Set<String> headers = counters.get(0).getAttributes().keySet();
        CSVFormat format = getFormat(headers);

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (CounterDef counter : counters) {
                Map<String, String> attrs = counter.getAttributes();
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    // Prioritize explicit fields if they might have changed
                    if ("id".equalsIgnoreCase(header)) {
                        values.add(counter.getId());
                    } else if ("name".equalsIgnoreCase(header)) {
                        values.add(counter.getName());
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
                    if ("idf_moc_def".equalsIgnoreCase(header)) {
                        values.add(moc.getId());
                    } else {
                        values.add(attrs.get(header));
                    }
                }
                printer.printRecord(values);
            }
        }
    }
}
