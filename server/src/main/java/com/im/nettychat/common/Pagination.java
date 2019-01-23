package com.im.nettychat.common;

import lombok.Data;

/**
 * @author hejianglong
 * @date 2019/1/22.
 */
public class Pagination {

    private int page;

    private int size;

    private int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size > 300 ? 300 : size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
