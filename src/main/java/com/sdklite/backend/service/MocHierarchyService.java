package com.sdklite.backend.service;

import com.sdklite.backend.model.MocDef;
import com.sdklite.backend.model.MocDefParent;
import com.sdklite.backend.model.VendorMocDef;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MocHierarchyService {

    public List<MocDef> buildTree(List<MocDef> mocs, List<MocDefParent> parents, List<VendorMocDef> vendorMocs) {
        Map<String, MocDef> mocMap = mocs.stream()
                .collect(Collectors.toMap(MocDef::getId, Function.identity()));

        // Link Vendor MOCs
        for (VendorMocDef vm : vendorMocs) {
            MocDef moc = mocMap.get(vm.getMocDefId());
            if (moc != null) {
                moc.getVendorMocDefs().add(vm);
            }
        }

        // Build Tree
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
