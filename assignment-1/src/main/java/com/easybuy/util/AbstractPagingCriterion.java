package com.easybuy.util;

/**
 * A paging criterion utilizes the paging calculation work, providing start/end row number on a specified page as well
 * as moveToNextPage()/moveToPreviousPage().
 *
 * @author maxiaohao <maxiaohao@gmail.com>
 * @date Sep 30, 2016
 *
 */
public abstract class AbstractPagingCriterion {

    public static final int DEFAULT_PAGE_SIZE = 10;

    int pageSize = DEFAULT_PAGE_SIZE;

    /*
     * 1-based
     */
    int currentPage = 1;
    int totalPageCount = 0;
    long totalRowCount = 0;


    public AbstractPagingCriterion(long totalRowCount) throws Exception {
        super();
        this.totalRowCount = totalRowCount;
        calculateNumbers();
    }


    public AbstractPagingCriterion(long totalRowCount, int currentPage) throws Exception {
        this(totalRowCount);
        this.currentPage = currentPage;
        calculateNumbers();
    }


    public AbstractPagingCriterion(long totalRowCount, int currentPage, int pageSize) throws Exception {
        this(totalRowCount, currentPage);
        this.pageSize = pageSize;
        calculateNumbers();
    }


    private void calculateNumbers() throws Exception {
        if (totalRowCount < 0) {
            throw new Exception("totalRowCount should be greater than 0");
        }
        if (pageSize < 1) {
            throw new Exception("pageSize should be at least 1");
        }
        long totalPageCount = totalRowCount / pageSize + (0 == totalRowCount % pageSize ? 0 : 1);
        if (0 == totalPageCount) {
            // no data at all
            currentPage = 0;
        } else if (currentPage < 1 || currentPage > totalPageCount) {
            throw new Exception("currentPage should be between 1 and " + totalPageCount + ", while totalRowCount=="
                    + totalRowCount + ", pageSize==" + pageSize + ")");
        }

    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) throws Exception {
        this.pageSize = pageSize;
        calculateNumbers();
    }


    public int getCurrentPage() {
        return currentPage;
    }


    public void setCurrentPage(int currentPage) throws Exception {
        this.currentPage = currentPage;
        calculateNumbers();
    }


    /**
     * Move to the next page.
     *
     * @return true if move succeeded, false otherwise.
     * @throws Exception
     */
    public boolean moveToNextPage() throws Exception {
        if (currentPage < totalPageCount) {
            currentPage++;
            calculateNumbers();
            return true;
        } else {
            return false;
        }
    }


    /**
     * Move to the previous page.
     *
     * @return true if move succeeded, false otherwise.
     * @throws Exception
     */
    public boolean moveToPreviousPage() throws Exception {
        if (currentPage > 1) {
            currentPage--;
            calculateNumbers();
            return true;
        } else {
            return false;
        }
    }


    public int getTotalPageCount() {
        return totalPageCount;
    }


    public long getTotalRowCount() {
        return totalRowCount;
    }


    public void setTotalRowCount(long totalRowCount) throws Exception {
        this.totalRowCount = totalRowCount;
        calculateNumbers();
    }


    public long getCurrentPageRowStart() {
        return (currentPage - 1) * pageSize;
    }


    public long getCurrentPageRowEnd() {
        return Math.min(currentPage * pageSize - 1, totalRowCount - 1);
    }

}
