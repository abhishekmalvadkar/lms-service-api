package com.amalvadkar.lms.tags.dao;

import com.amalvadkar.lms.common.enums.ResponseMessageEnum;
import com.amalvadkar.lms.common.models.response.CustomResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagDao {

    private final EntityManager entityManager;


    public CustomResponse updateTag(String column, Object value, String tableName, String userId, String tagId){

        String stringQuery = String.format("update %s as t set %s = :name , updated_on = UTC_TIMESTAMP() ,updated_by = :userId where t.id = :tagId and delete_flag = false", tableName, column);

        Query nativeUpdateQuery = entityManager.createNativeQuery(stringQuery);
        nativeUpdateQuery.setParameter("name", value);
        nativeUpdateQuery.setParameter("userId", userId);
        nativeUpdateQuery.setParameter("tagId", tagId);

        int updatedRow = nativeUpdateQuery.executeUpdate();

       return CustomResponse.success(Map.of("tagId", tagId), ResponseMessageEnum.UPDATED_SUCCESSFULLY_MSG.value());



    }

}
