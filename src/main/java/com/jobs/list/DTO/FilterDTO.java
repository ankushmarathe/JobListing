package com.jobs.list.DTO;

public class FilterDTO {

	private String skill;
	private String location;
	private String company;
	public FilterDTO(String skill, String location, String company) {
		super();
		this.skill = skill;
		this.location = location;
		this.company = company;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
}
