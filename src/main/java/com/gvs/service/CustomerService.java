package com.gvs.service;

import com.gvs.dto.APIResponseDTO;
<<<<<<< HEAD
import com.gvs.model.sap.entity.CustomerDataViewEntity;
=======
>>>>>>> 5971ad3519d484503658e788e6d1cde69836658e
import com.gvs.model.sap.entity.CustomerTableEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface CustomerService {
    public ResponseEntity<APIResponseDTO<Set<CustomerTableEntity>>> getCustomersWithManualPagination(int page, int rows, String searchValue, String selectedFilter);
    public CustomerDataViewEntity getCustomerDataView(String nit);

}
