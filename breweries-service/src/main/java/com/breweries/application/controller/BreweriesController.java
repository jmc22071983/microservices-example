package com.breweries.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.ConjunctionQuery;
import com.couchbase.client.java.search.result.SearchQueryResult;
import com.couchbase.client.java.search.result.SearchQueryRow;
import com.couchbase.client.java.subdoc.DocumentFragment;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Breweries Service", tags = {"Breweries API Endpoints"})
@Produces({"application/json"})
public class BreweriesController {
	private static final Gson gson = new Gson();
	private static Cluster cluster;
	private static final Logger LOGGER = LoggerFactory.getLogger(BreweriesController.class);
	private static final String BEER_SAMPLE = "beer-sample";
	private static final String PASS = "sysadmin";
	
	private static Bucket openBucket(String bucketName) {
		cluster = CouchbaseCluster.create("127.0.0.1:8091:8091");
		//cluster = CouchbaseCluster.create(System.getenv("COUCHBASE_ADDR"));
		return cluster.openBucket(bucketName, PASS);
	}
	
	@GetMapping("/search-breweries-by-location")
	@ApiOperation(value = "Find breweries by location", produces="application/json")
	public String findBreweries(@RequestParam String location) {  
		List<Map<String,String>> sresult = new ArrayList<>();
		ConjunctionQuery fts = SearchQuery.conjuncts(SearchQuery.term("brewery").field("type"));
	    fts.and(
	    		SearchQuery.disjuncts(
	    		SearchQuery.matchPhrase(location).field("country"),
	    		SearchQuery.matchPhrase(location).field("city")
	    		)
	    		);
	    Bucket bucket = openBucket(BEER_SAMPLE);
	    SearchQuery query = new SearchQuery("brewery", fts).limit(100);
	    SearchQueryResult result = bucket.query(query);	   
	    for (SearchQueryRow row : result) {
	        DocumentFragment<Lookup> fragment = bucket
	                .lookupIn(row.id())
	                .get("country")
	                .get("city")
	                .get("name")
	                .get("phone")
	                .get("address")	             
	                .execute();
	        Map<String,String> map = new HashMap<>();
	        map.put("id",row.id());
	        if(fragment.content(0) != null) {
	            map.put("country", fragment.content(0).toString());
	        }
	        if(fragment.content(1) != null) {
	        	 map.put("city", fragment.content(1).toString());
	        }
	        if(fragment.content(2) != null) {
	        	 map.put("name", fragment.content(2).toString());
	        }
	        if(fragment.content(3) != null) {
	        	 map.put("phone", fragment.content(3).toString());
	        }
	        if(fragment.content(4) != null) {
	        	 map.put("address", fragment.content(4).toString());
	        }
	        sresult.add(map);
	    }
	    return  gson.toJson(sresult);
	}
}
