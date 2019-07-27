package com.st18rai.moviesapp.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse<T> {
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<T> dataList = null;

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getDataList() {
        return dataList;
    }
}
