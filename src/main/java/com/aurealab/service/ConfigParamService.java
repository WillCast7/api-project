package com.aurealab.service;

import com.aurealab.dto.ConfigParamDTO;

import java.util.Set;

public interface ConfigParamService {
    Set<ConfigParamDTO> getConfigParamsByParent(String parent);
}
