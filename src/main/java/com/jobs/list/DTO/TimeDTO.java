package com.jobs.list.DTO;

import java.util.List;
import java.util.Set;

import com.jobs.list.model.Job;

public class TimeDTO {

	private long milliseconds;
	private List<Job> jobs;
	
	public TimeDTO(long milliseconds, List<Job> jobs) {
		super();
		this.milliseconds = milliseconds;
		this.jobs = jobs;
	}
	public long getMilliseconds() {
		return milliseconds;
	}
	public void setMilliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
}
