package com.simpletak.takscheduler.dto.eventGroup;
import com.simpletak.takscheduler.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGroupResponseDTO {
    private UUID eventGroupId;
    private String eventGroupName;
    private String eventGroupDescription;
    private UUID owner;
}
