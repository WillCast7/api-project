package com.gvs.service;

import com.gvs.dto.ConfigParamDTO;
import com.gvs.model.crm.entity.ConfigParamsEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface ConfigParamService {
    Set<ConfigParamDTO> getConfigParamsByParent(String parent);
}
