package com.system.util;

import java.util.List;

/*
 * 分页工具类
 * */
public class Page<T> {
	//一页显示的记录数
    private int numPerPage;
    //记录总数
    private int total;
    //总页数
    private int totalPages;
    //当前页码
    private int currentPage;
    //起始行数(数据记录数 LIMIT ?,5)
    private int startIndex;
    //结束行数(数据记录数 LIMIT ?,5)
    private int lastIndex;
    //结果集存放List
    private List<T> rows;
    
    public Page(int currentPage,int numPerPage,int totalRows,List<T> list){
    	//设置要显示的页数 当前第几页
        setCurrentPage(currentPage);
    	//设置每页显示记录数 每页显示的行数
        setNumPerPage(numPerPage);
        //总记录数
        setTotal(totalRows);
        //计算总页数
        setTotalPages();
        //计算起始行数
        setStartIndex();
        //计算结束行数
        setLastIndex();
        //装入结果集
        setRows(list);
    }
    
    private void setTotalPages(){
    	int totalPage = 0;
    	if(total % numPerPage==0){
    		totalPage = total /numPerPage;
    	}else{
    		totalPage = (total /numPerPage)+1;
    	}
    	setTotalPages(totalPage);
    }
    
    private void setStartIndex(){
    	setStartIndex((currentPage - 1) * numPerPage);
    }
    
    private void setLastIndex(){
    	if( total < numPerPage){
            this.lastIndex = total;
        }else if((total % numPerPage == 0) || (total % numPerPage != 0 && currentPage < totalPages)){
            this.lastIndex = currentPage * numPerPage;
        }else if(total % numPerPage != 0 && currentPage == totalPages){//最后一页
            this.lastIndex = total ;
        }
    }
    
	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
