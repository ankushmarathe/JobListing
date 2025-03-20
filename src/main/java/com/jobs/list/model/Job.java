package com.jobs.list.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class Job {

	private String jobTitle;
	private String company;
	private String location;
	private String posted;
	private String url;
	
	@Override
	public String toString() {
		return "Job [jobTitle=" + jobTitle + ", company=" + company + ", location=" + location + ", posted=" + posted
				+ ", url=" + url + "]";
	}

	public Job(String jobTitle, String company, String location, String posted, String url) {
		super();
		this.jobTitle = jobTitle;
		this.company = company;
		this.location = location;
		this.posted = posted;
		this.url = url;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPosted() {
		return posted;
	}

	public void setPosted(String posted) {
		this.posted = posted;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
