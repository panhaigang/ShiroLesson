package cn.et.lesson02.utils;

import java.util.List;

public class PageTools {
    /**
     * 
     * 
     * @param curPage   页锟芥传锟斤拷牡锟角耙�
     * @param totalCount   锟斤拷菘锟斤拷询锟斤拷锟杰硷拷录锟斤拷
     * @param pageCount     每页锟斤拷示锟斤拷锟斤拷锟斤拷
     */
    public PageTools(Integer curPage,Integer totalCount,Integer pageCount){
        this.curPage=curPage;
        this.total=totalCount;
        this.pageCount=pageCount==null?this.pageCount:pageCount;
        //锟斤拷一页
        this.prePage=(curPage==1?1:curPage-1);
        //锟斤拷页锟斤拷
        this.totalPage=totalCount%this.pageCount==0?totalCount/this.pageCount:(totalCount/this.pageCount+1);
        //锟斤拷一页
        this.nextPage=(curPage==totalPage)?totalPage:(curPage+1);
        //锟斤拷菘锟节硷拷页每页锟斤拷示锟斤拷锟斤拷锟�   锟斤拷始锟斤拷锟斤拷徒锟斤拷锟斤拷锟斤拷锟�
        this.startIndex=(curPage-1)*this.pageCount+1;
        this.endIndex=curPage*this.pageCount;
    }
    
    
    
    /**
     * 锟斤拷前页(锟斤拷态  锟斤拷页锟芥传锟斤拷)
     */
    private Integer curPage;
    /**
     * 锟斤拷一页
     * prePage=(curPage==1?1:curPage-1)
     * 
     */
    private Integer prePage;
    /**
     * 锟斤拷一页
     * nextPage=(nextPage==totalPage)?totalPage:(curPage+1)
     * 
     * 
     */
    private Integer nextPage;
    //每页锟斤拷示锟斤拷锟斤拷锟斤拷
    private Integer pageCount=10;
    /**
     * 锟斤拷页锟斤拷
     * totalPage=(totalCount%pageCount==0?totalCount/pageCount:(totalCount/pageCount+1)
     * 
     */
    private Integer totalPage;
    //锟斤拷菘锟斤拷锟杰硷拷录锟斤拷(锟斤拷菘锟斤拷询)
    private Integer total;
    //每页锟斤拷锟斤拷莘锟斤拷锟矫硷拷锟斤拷锟斤拷
    private List rows;
    
    /**
     * 锟斤拷菘饪硷拷锟斤拷锟酵斤拷锟斤拷锟斤拷锟斤拷
     * 
     * startIndex=(curPage-1)*PageCount+1
     * endIndex=curPage*pageCount
     * 
     */
    private int startIndex;
    private int endIndex;
    
    public int getStartIndex() {
        return startIndex;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    public int getEndIndex() {
        return endIndex;
    }
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
    public Integer getCurPage() {
        return curPage;
    }
    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }
    public Integer getPrePage() {
        return prePage;
    }
    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }
    public Integer getNextPage() {
        return nextPage;
    }
    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }
    public Integer getPageCount() {
        return pageCount;
    }
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
    public Integer getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotalt(Integer total) {
        this.total = total;
    }
    
    
    public static void main(String[] args) {
        int curPage=6;
        int totalCount=27;
        int pageCount=5;
        
        PageTools pt=new PageTools(curPage, totalCount, pageCount);
        System.out.println(pt.getNextPage());
        System.out.println(pt.getPrePage());
        System.out.println(pt.getTotalPage());
        System.out.println(pt.getStartIndex());
        System.out.println(pt.getEndIndex());
    }
}