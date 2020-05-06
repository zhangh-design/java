package com.user.lspolygon.uicomponent.domain;

import java.io.Serializable;

//危控区类型
public class PoygonType implements Serializable{
	private static final long serialVersionUID = 1L;

	private int rid;
	private String name;
	private String en_name;
	private String stroke;
	private String strokeOpacity;
	private String strokeWidth;
	private String fill;
	private String fillOpacity;
	private String icon;
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEn_name() {
		return en_name;
	}
	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	public String getStroke() {
		return stroke;
	}
	public void setStroke(String stroke) {
		this.stroke = stroke;
	}
	public String getStrokeOpacity() {
		return strokeOpacity;
	}
	public void setStrokeOpacity(String strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}
	public String getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(String strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String getFill() {
		return fill;
	}
	public void setFill(String fill) {
		this.fill = fill;
	}
	public String getFillOpacity() {
		return fillOpacity;
	}
	public void setFillOpacity(String fillOpacity) {
		this.fillOpacity = fillOpacity;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
