package com.jobs.list.controller;

import java.util.Set;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobs.list.DTO.FilterDTO;
import com.jobs.list.DTO.TimeDTO;
import com.jobs.list.model.Job;
import com.jobs.list.services.CrawlService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jobs")
public class JobController {

	private final CrawlService crawlService;
	
	JobController(CrawlService crawlService){
		this.crawlService = crawlService;
	}
	
	@GetMapping("/")
    public String home() {
        return "index"; 
    }
	
	@GetMapping(value="/search")
	public Mono<TimeDTO> getJobs(@RequestParam(required = false) String skill,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String company) {
		String temp ="https://www.linkedin.com/jobs/search?keywords="+skill+"&location="+location+"&geoId=102713980&trk=public_jobs_jobs-search-bar_search-submit&position=1&pageNum=0";
		FilterDTO filterDTO=new FilterDTO(skill, location, company);
		return crawlService.start(temp, filterDTO);		
	}
	
//	@GetMapping(value="/search/withOutExecutor")
//	public Mono<Set<Job>> getJobsWithOutExecutor() {
//		String temp ="https://www.linkedin.com/jobs/search?keywords=java&location=India&geoId=102713980&trk=public_jobs_jobs-search-bar_search-submit&position=1&pageNum=0";
//		return crawlService.withOutExecutor(temp);
//	}
}
