package com.unisinos.mse.mapper;

import com.unisinos.mse.entity.MaterialEntity;
import com.unisinos.mse.model.Material;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialMapper {
    public static List<Material> mapToMaterialList(List<MaterialEntity> materialEntities) {
        if (!ObjectUtils.isEmpty(materialEntities)) {
            return materialEntities.stream()
                    .map(MaterialMapper::mapToMaterial)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static Material mapToMaterial(MaterialEntity materialEntity) {
        return Material.builder()
                .descricao(materialEntity.getDescricao())
                .codigo(materialEntity.getCodigo())
                .quantidade(materialEntity.getQuantidade())
                .validado(materialEntity.getValidado())
                .build();
    }
}
