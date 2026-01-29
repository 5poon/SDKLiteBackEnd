package com.sdklite.backend.service;

import com.sdklite.backend.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MocHierarchyService {

    public List<MocDef> buildTree(List<MocDef> mocs, 
                                  List<MocDefParent> parents, 
                                  List<VendorMocDef> vendorMocs,
                                  List<CounterDef> counters,
                                  List<MocAttributeDef> attributes,
                                  List<ImportCounterFor> counterMappings,
                                  List<ImportAttrFor> attributeMappings,
                                  List<CounterDefGran> granularities) {
        
        Map<String, MocDef> mocMap = mocs.stream()
                .collect(Collectors.toMap(MocDef::getId, Function.identity()));

        Map<String, VendorMocDef> vendorMocMap = vendorMocs.stream()
                .collect(Collectors.toMap(VendorMocDef::getId, Function.identity()));

        // Link Vendor MOCs to logical MOCs
        for (VendorMocDef vm : vendorMocs) {
            MocDef moc = mocMap.get(vm.getMocDefId());
            if (moc != null) {
                moc.getVendorMocDefs().add(vm);
            }
        }

        // Link Attributes to Vendor MOCs and logical MOCs
        Map<String, MocAttributeDef> attrMap = attributes.stream()
                .collect(Collectors.toMap(MocAttributeDef::getId, Function.identity()));
        
        for (ImportAttrFor mapping : attributeMappings) {
            VendorMocDef vm = vendorMocMap.get(mapping.getVendorMocDefId());
            MocAttributeDef attr = attrMap.get(mapping.getAttrImportId());
            if (vm != null && attr != null) {
                // Binding to logical MOC for easy tree navigation
                MocDef moc = mocMap.get(vm.getMocDefId());
                if (moc != null && !moc.getMocAttributeDefs().contains(attr)) {
                    moc.getMocAttributeDefs().add(attr);
                }
            }
        }

        // Link Counters to logical MOCs
        Map<String, CounterDef> counterMap = counters.stream()
                .collect(Collectors.toMap(CounterDef::getId, Function.identity()));
        Map<String, CounterDefGran> granMap = granularities.stream()
                .collect(Collectors.toMap(CounterDefGran::getId, Function.identity()));

        for (ImportCounterFor mapping : counterMappings) {
            VendorMocDef vm = vendorMocMap.get(mapping.getVendorMocDefId());
            CounterDefGran gran = granMap.get(mapping.getCounterDefGranId());
            if (vm != null && gran != null) {
                CounterDef counter = counterMap.get(gran.getCounterDefId());
                if (counter != null) {
                    MocDef moc = mocMap.get(vm.getMocDefId());
                    if (moc != null && !moc.getCounters().contains(counter)) {
                        moc.getCounters().add(counter);
                    }
                }
            }
        }

        // Build Tree Structure
        List<MocDef> roots = new ArrayList<>();
        Map<String, String> childToParent = parents.stream()
                .collect(Collectors.toMap(MocDefParent::getChildId, MocDefParent::getParentId, (a, b) -> a));

        for (MocDef moc : mocs) {
            String parentId = childToParent.get(moc.getId());
            if (parentId != null && mocMap.containsKey(parentId)) {
                mocMap.get(parentId).getChildren().add(moc);
            } else {
                roots.add(moc);
            }
        }

        return roots;
    }
}
