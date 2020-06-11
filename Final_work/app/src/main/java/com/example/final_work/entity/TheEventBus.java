package com.example.final_work.entity;

import lombok.Data;

public class TheEventBus {

    @Data
    public static class ClearPositionEvent {
        public Boolean isClear;

        public ClearPositionEvent(Boolean isClear) {
            this.isClear = isClear;
        }
    }
}
