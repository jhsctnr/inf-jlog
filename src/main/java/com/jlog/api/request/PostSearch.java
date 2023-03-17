package com.jlog.api.request;


import lombok.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PostSearch {

    private static final long MAX_SIZE = 2000L;

    @Builder.Default
    private Long page = 1L;
    @Builder.Default
    private Long size = 10L;

    public long getOffset() {
        return (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}
