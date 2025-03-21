package com.jobs.list.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.jobs.list.DTO.FilterDTO;
import com.jobs.list.DTO.TimeDTO;
import com.jobs.list.model.Job;

import reactor.core.publisher.Mono;

@Service
public class CrawlService {
	private static final int MAX_THREADS = 10;  
    private static final int MAX_DEPTH = 2;    // Maximum depth to crawl
    private static ExecutorService executor;
    private static Set<Job> visitedUrls ;
	
	public Mono<TimeDTO> start(String url, FilterDTO filterDTO) {
		long startTime = System.currentTimeMillis();
		executor = Executors.newFixedThreadPool(MAX_THREADS);
		visitedUrls = Collections.synchronizedSet(new HashSet<>());
	    getJobs(url, 0);
        executor.shutdown();
        while (!executor.isTerminated()) {
            System.out.println("Waiting for tasks to complete...");
            if(System.currentTimeMillis() - startTime > (long)7000) break;
        }
        System.out.println("Done");
        List<Job> list=new ArrayList<>(visitedUrls);
        list.sort(Comparator.comparing(Job::getJobTitle)
                .thenComparing(Job::getLocation)
                .thenComparing(Job::getCompany));
        
        String preferredSkill = filterDTO.getSkill();       
        String preferredLocation = filterDTO.getLocation();      
        String preferredCompany = filterDTO.getCompany();

        list.sort(Comparator.comparingInt(job -> 
            (isMatch(job.getJobTitle(), preferredSkill) ? 0 : 1) + 
            (isMatch(job.getLocation(), preferredLocation) ? 0 : 1) + 
            (isMatch(job.getCompany(), preferredCompany) ? 0 : 1)
        ));
        
        
        long totalTime = System.currentTimeMillis() - startTime;
        
        return Mono.just(new TimeDTO(totalTime, list));
	}
	
	private static boolean isMatch(String jobField, String preferredField) {
        if (preferredField == null || preferredField.isEmpty()) {
            return false;
        }
        return jobField != null && jobField.equalsIgnoreCase(preferredField);
    }
	
	public void getJobs(String url, int depth) {
		if (depth > MAX_DEPTH ) return;
		
		executor.execute(() -> {
			try {

	            Document doc = Jsoup.connect(url).get();
	
	            Elements jobElements = doc.select(".base-search-card__info"); 
	            for (Element job : jobElements) {
	                String title = job.select(".base-search-card__title").text(); 
	                String company = job.select(".base-search-card__subtitle").text();
	                String location = job.select(".job-search-card__location").text();
	                String posted = job.select(".job-search-card__listdate--new").text();
	                Elements links = job.select("a");
	                String nextUrl="";
	                for (Element link : links) {
	                	nextUrl  = link.absUrl("href");
	                }
	                
	                Job tempJob = new Job(title, company, location, posted, nextUrl);
	                visitedUrls.add(tempJob);
	                if (!visitedUrls.contains(tempJob)) {
                        getJobs(url, depth + 1);
                    }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		});
	}
}
