package org.green.career.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RatingRequestDto {

    private int jNo;
    private int rNo;
    private float rating;
    private String id;
}
