package com.sdklite.backend.service;

import com.sdklite.backend.model.MocDef;
import com.sdklite.backend.model.MocDefParent;
import com.sdklite.backend.model.VendorMocDef;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MocHierarchyServiceTest {

    private final MocHierarchyService service = new MocHierarchyService();

    @Test
    void buildTree_ShouldCreateHierarchy() {
        MocDef m1 = new MocDef(); m1.setId("1"); m1.setName("Parent");
        MocDef m2 = new MocDef(); m2.setId("2"); m2.setName("Child");
        List<MocDef> mocs = List.of(m1, m2);

        MocDefParent p1 = new MocDefParent();
        p1.setChildId("2");
        p1.setParentId("1");
        List<MocDefParent> parents = List.of(p1);

        List<MocDef> roots = service.buildTree(mocs, parents, Collections.emptyList());

        assertEquals(1, roots.size());
        assertEquals("1", roots.get(0).getId());
        assertEquals(1, roots.get(0).getChildren().size());
        assertEquals("2", roots.get(0).getChildren().get(0).getId());
    }
}
