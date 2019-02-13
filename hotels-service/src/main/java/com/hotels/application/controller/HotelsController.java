package com.hotels.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
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
@Api(value="Find Hotels", tags = {"Hotels API Endpoints"})
@Produces({"application/json"})
public class HotelsController {
	private static final Gson gson = new Gson();
	private static Cluster cluster;
	private static final Logger LOGGER = LoggerFactory.getLogger(HotelsController.class);
	private static final String TRAVEL_SAMPLE = "travel-sample";
	private static final String PASS = "sysadmin";
	private static final String Q_HOTELS = "SELECT country, city, address, name, phone FROM `travel-sample` WHERE type = 'hotel'";
	
	private static Bucket openBucket(String bucketName) {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
                .connectTimeout(10000)
                .build();
		LOGGER.info("COUCHBASE_ADDR, {}", System.getenv("COUCHBASE_ADDR"));
		cluster = CouchbaseCluster.create(env,System.getenv("COUCHBASE_ADDR"));
		return cluster.openBucket(bucketName, PASS);
	}
	
	
	@GetMapping("/hotels")
	@ApiOperation(value = "Get all hotels", produces="application/json")
	public static String allHotels() {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_HOTELS));
        cluster.disconnect();
		return result.allRows().toString();
	}
	
	@GetMapping("/search-hotels-by-location")
	@ApiOperation(value = "Search hotels by location", produces="application/json")
	public String hotelByLocation(@RequestParam String location) {  
		List<Map<String,String>> sresult = new ArrayList<>();
		ConjunctionQuery fts = SearchQuery.conjuncts(SearchQuery.term("hotel").field("type"));
	    fts.and(
	    		SearchQuery.disjuncts(
	    		SearchQuery.matchPhrase(location).field("country"),
	    		SearchQuery.matchPhrase(location).field("city")
	    		)
	    		);
	    Bucket bucket = openBucket(TRAVEL_SAMPLE);
	    SearchQuery query = new SearchQuery("travel", fts).limit(100);
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
	
	
	@GetMapping("/retrieve-location-by-hotel-name")
	@ApiOperation(value = "Retrieve city from hotel name", produces="application/json")
	public String retrieveCityFromHotelName(@RequestParam String hotelName) {  
		ConjunctionQuery fts = SearchQuery.conjuncts(SearchQuery.term("hotel").field("type"));
	    fts.and(
	    		SearchQuery.disjuncts(
	    		SearchQuery.matchPhrase(hotelName).field("name")
	    		)
	    		);
	    Bucket bucket = openBucket(TRAVEL_SAMPLE);
	    SearchQuery query = new SearchQuery("travel", fts).limit(100);
	    SearchQueryResult result = bucket.query(query);	   
	    for (SearchQueryRow row : result) {
	        DocumentFragment<Lookup> fragment = bucket
	                .lookupIn(row.id())
	                .get("city")	              
	                .execute();	   
	        if(fragment.content(0) != null) {
	        	 return fragment.content(0).toString();
	        }
	    }
	    return  null;
	}
	
}
	