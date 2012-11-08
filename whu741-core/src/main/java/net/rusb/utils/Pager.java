package net.rusb.utils;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> {
	/**
	 * 默认页面大小为10
	 */
	public final static int DEFAULT_PAGE_SIZE = 15;
	/**
	 * 数据对象的列表
	 */
	private List<T> voList;
	/**
	 * 当前页序号
	 */
	private int pageIndex = 1;
	/**
	 * 页面大小
	 */
	private int pageSize = 0;
	/**
	 * 页面总数
	 */
	private int pageCount = 0;
	/**
	 * 记录总数
	 */
	private long recodeCount = 0;
	
	/**
	 * 页序号列表
	 */
	private List<Integer> pageIndexArray = new ArrayList<Integer>();
	
	public List<Integer> getPageIndexArray() {
		return pageIndexArray;
	}
	public Pager(){};
	public Pager(int pageSize,int pageIndex) {
		this.pageSize = pageSize;
		setPageIndex(pageIndex);
	}
	public Pager(int pageIndex) {
		setPageIndex(pageIndex);
	}
	
	public List<T> getVoList() {
		return voList;
	}
	public void setVoList(List<T> voList) {
		this.voList = voList;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	private void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize==0?DEFAULT_PAGE_SIZE:pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public int getNextPageIndex() {
		if(pageIndex<pageCount)
			return pageIndex + 1;
		else
			return pageCount;
	}
	public int getPrePageIndex() {
		if(pageIndex>1)
			return pageIndex - 1;
		else
			return 1;
	}
	public long getRecodeCount() {
		return recodeCount;
	}
	public void setRecodeCount(long recodeCount) {
		this.recodeCount = recodeCount;
		this.pageCount = (int)recodeCount%getPageSize()==0?(int)recodeCount/getPageSize():(int)recodeCount/getPageSize()+1;
		System.out.println("pageCount:==="+pageCount);
		for(int i = 1;i<=pageCount;i++){
			if(i>5)break;
			pageIndexArray.add(i);
		}
	}
}
