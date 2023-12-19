package hust.project.base.modified.Model;

import java.util.List;

public interface ModifiedRepository {

    void updateAcceptModifiedStatus(String requestIdO);

    List<ModifiedDTO> getAllModifiedDTO();

    void updateRejectModifiedStatus(String requestId);

    void updateAcceptModifiedRecordId(String requestId, String recordId);

}
