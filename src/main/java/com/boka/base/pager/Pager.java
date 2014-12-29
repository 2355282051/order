package com.boka.base.pager;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

public class Pager<T> {
	private static int DEFAULT_PAGE_SIZE = 10;
	
	public long startRecord; //当前开始的记录索引
	public int pageSize; //每页显示记录数
	
	public int currentPage;//当前第几页
	public int totalPage ; //总页数
	public long totalRecord;  //总记录数
	
	public List<T> data; // 当前页中存放的记录,类型一般为List
	
	public Pager() {
	}

	public Pager(long totalRecord, List<T> data) {
		this.totalRecord = totalRecord;
		this.data = data;
	}
	
	public Pager(int currentPage, int pageSize ) {
		this.pageSize = pageSize;
		this.currentPage = validCurrentPage(currentPage, totalPage);
	}
	
	
	public Pager(int currentPage, int pageSize, long totalRecord) {
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		this.totalPage = (int)Math.ceil(((double)totalRecord+(double)pageSize)/(double)pageSize);
		this.currentPage = validCurrentPage(currentPage, totalPage);
	}
	
	public Pager(int startRecord,int currentPage, int pageSize, long totalRecord,
			List<T> data) {
		this.startRecord = startRecord;
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		this.totalPage = (int)Math.ceil((double)totalRecord/(double)pageSize);
		this.currentPage = validCurrentPage(currentPage, totalPage);
		this.data = data;
	}

	public Pager(RowBounds rowBounds) {   
        this.startRecord = rowBounds.getOffset();
        this.pageSize = rowBounds.getLimit();
    } 
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = validCurrentPage(currentPage, this.totalPage);
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		this.totalPage = (int)Math.ceil(((double)totalRecord+(double)pageSize)/(double)pageSize);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	private int validCurrentPage(int currentPage, int totalPage){
		if(currentPage > totalPage){
			currentPage = totalPage;
		}else if(currentPage <= 0){
			currentPage = 1;
		}
		return currentPage;
	}
	
	public static int validPageNo(int startRecord, int pageSize,long totalRecord){
		int currentPage = 0;
		currentPage = startRecord/pageSize + 1;
		
		int tmpTotalPage = 0 ;
		tmpTotalPage = (int)Math.ceil((double)totalRecord/(double)pageSize);
		if(currentPage > tmpTotalPage){
			currentPage = tmpTotalPage;
		}else if(currentPage <= 0){
			currentPage = 1;
		}
		return currentPage;
	}
	
	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		
		return (pageNo - 1) * pageSize;
	}

	/**
	 * @return the startRecord
	 */
	public long getStartRecord() {
		return startRecord;
	}

	/**
	 * @param startRecord the startRecord to set
	 */
	public void setStartRecord(long startRecord) {
		this.startRecord = startRecord;
	}
}
