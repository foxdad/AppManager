package it.lt.entity;

import java.util.Date;
import java.util.Objects;

public class DataDictionary {
	private Integer id;//主键id
	private String typeCode;//类型编码
	private String typeName;//类型名称
	private Integer valueId;//类型值ID
	private String valueName;//类型值name
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
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getValueId() {
		return valueId;
	}
	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
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
		DataDictionary that = (DataDictionary) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(typeCode, that.typeCode) &&
				Objects.equals(typeName, that.typeName) &&
				Objects.equals(valueId, that.valueId) &&
				Objects.equals(valueName, that.valueName) &&
				Objects.equals(createdBy, that.createdBy) &&
				Objects.equals(creationDate, that.creationDate) &&
				Objects.equals(modifyBy, that.modifyBy) &&
				Objects.equals(modifyDate, that.modifyDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, typeCode, typeName, valueId, valueName, createdBy, creationDate, modifyBy, modifyDate);
	}
}
