package com.stock.exercise.model.stock;

import java.time.LocalDate;

public class Share {
    private LocalDate date;
    private double share;

    public Share(double share, LocalDate date) {
        this.share = share;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getShare() {
        return share;
    }
}
