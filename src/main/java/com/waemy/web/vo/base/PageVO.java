package com.waemy.web.vo.base;

/**
 * Users: xueyanbeijing Date: 11-10-11 Time: 下午6:52 分页VO
 */
public class PageVO {
    
    private int pageNo = 1;// 当前页号
    
    private long totalCount = 0;// 总记录条数
    
    private boolean nextPage; // 是否有下一页
    
    // TODO 扩展用的，客户端不需要此信息
    private int pageSize = 10;
    
    private int totalPage;// 总页数
    
    private int startRow;
    
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
    
    public int getStartRow() {
        startRow = (pageNo - 1) * pageSize;
        return startRow;
    }
    
    public int getTotalPage() {
        if (pageSize <= 0) pageSize = 10;
        int totalPage = (int) totalCount / pageSize;
        if (totalCount % pageSize == 0) {
            if (totalPage <= 1)
                return 1;
            else {
                this.totalPage = totalPage;
                setTotalPage(this.totalPage);
                return this.totalPage;
            }
        } else// 如果不被整除就加1
        {
            this.totalPage = totalPage + 1;
            setTotalPage(this.totalPage);
            return this.totalPage;
            
        }
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public int getPageNo() {
        // if (pageNo>totalPage)//如果下一页大于最大页，则自动置为第一页
        // // pageNo = 1;
        // pageNo=totalPage;
        // if(pageNo<0){
        // pageNo=1;
        // }
        return pageNo;
    }
    
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    public long getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
    
    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }
    
    public boolean isNextPage() {
        if (pageNo * pageSize < totalCount)
            return true;
        else
            return false;
    }
    
    @Override
    public String toString() {
        return "PageVO{" + "pageNo=" + pageNo + ", totalCount=" + totalCount + ", nextPage=" + nextPage + ", pageSize=" + pageSize + ", totalPage=" + totalPage + ", startRow=" + startRow + '}';
    }
}
