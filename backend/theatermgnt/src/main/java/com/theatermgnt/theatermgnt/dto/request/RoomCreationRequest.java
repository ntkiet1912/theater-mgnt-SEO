package com.theatermgnt.theatermgnt.dto.request;


import com.theatermgnt.theatermgnt.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomCreationRequest {

    @NotNull
    String cinemaId;

    @Size(min = 3, message = "ROOM_NAME_INVALID")
    String name;

    @NotNull
    RoomType roomType;

}
