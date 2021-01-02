package io;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyStatsOutput {
    private int month;

    private List<Integer> distributorsIds;

    public MonthlyStatsOutput() {
    }

    public MonthlyStatsOutput(int month) {
        this.month = month;
        this.distributorsIds = new ArrayList<Integer>();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(final List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
