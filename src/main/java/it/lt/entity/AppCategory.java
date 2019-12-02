package it.lt.entity;

import java.util.Date;
import java.util.Objects;

public class AppCategory {
	private Integer id;//主键id
	private String categoryCode;//分类编码
	private String categoryName;//分类名称
	private Integer parentId;//父级节点id
	private Integer createdBy;//创建者
	private Date creationDate;//创建时间
	private Integer modifyBy;//更新者
	private Date modifyDate;//更新时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AppCategory that = (AppCategory) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(categoryCode, that.categoryCode) &&
				Objects.equals(categoryName, that.categoryName) &&
				Objects.equals(parentId, that.parentId) &&
				Objects.equals(createdBy, that.createdBy) &&
				Objects.equals(creationDate, that.creationDate) &&
				Objects.equals(modifyBy, that.modifyBy) &&
				Objects.equals(modifyDate, that.modifyDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, categoryCode, categoryName, parentId, createdBy, creationDate, modifyBy, modifyDate);
	}
}
