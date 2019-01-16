package com.eureka.clients.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
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

@RestController
public class ExampleCouchbase {
	private static final Gson gson = new Gson();
	private static Cluster cluster;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleCouchbase.class);
	private static final String TRAVEL_SAMPLE = "travel-sample";
	private static final String BEER_SAMPLE = "beer-sample";
	private static final String PASS = "sysadmin";
	private static final String Q_AIRLINES = "SELECT id, name, country FROM `travel-sample` WHERE type = 'airline'";
	private static final String Q_AIRPORTS = "SELECT id, airportname, city, country, geo FROM `travel-sample` WHERE type = 'airport'";
	private static final String Q_HOTELS = "SELECT country, city, address, name, phone FROM `travel-sample` WHERE type = 'hotel'";
	
	private static Bucket openBucket(String bucketName) {
		cluster = CouchbaseCluster.create("127.0.0.1:8091:8091");
		//cluster = CouchbaseCluster.create(System.getenv("COUCHBASE_ADDR"));
		return cluster.openBucket(bucketName, PASS);
	}
	
	@RequestMapping(value="/all-airlines", method = RequestMethod.GET)
	public static String allAirlines() {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_AIRLINES));
        cluster.disconnect();
		return result.allRows().toString();
	}
	
	@RequestMapping(value="/airlines/{name}", method = RequestMethod.GET)
	public static String airlines(@PathVariable String name) {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		JsonDocument jsonD = bucket.get(name);
		cluster.disconnect();
		return jsonD.content().toString();
	}
	
	@RequestMapping(value="/all-airports", method = RequestMethod.GET)
	public static String allAirports() {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_AIRPORTS));
        cluster.disconnect();
		return gson.toJson(result.allRows().toString());
	}
	
	@GetMapping("/search-airport-by-location")
	public String airportByLocation(@RequestParam String location) {  
		List<Map<String,String>> sresult = new ArrayList<>();
		ConjunctionQuery fts = SearchQuery.conjuncts(SearchQuery.term("airport").field("type"));
	    fts.and(
	    		SearchQuery.disjuncts(
	    		SearchQuery.matchPhrase(location).field("country"),
	    		SearchQuery.matchPhrase(location).field("city")
	    		)
	    		);
	    Bucket bucket = openBucket(TRAVEL_SAMPLE);
	    SearchQuery query = new SearchQuery("airport", fts).limit(100);
	    SearchQueryResult result = bucket.query(query);	   
	    for (SearchQueryRow row : result) {
	        DocumentFragment<Lookup> fragment = bucket
	                .lookupIn(row.id())
	                .get("airportname")
	                .get("city")
	                .execute();
	        Map<String,String> map = new HashMap<>();
	        map.put("id",row.id());
	        if(fragment.content(0) != null) {
	        	 map.put("airportname", fragment.content(0).toString());	
	        }
	        if(fragment.content(1) != null) {
	        	 map.put("city", fragment.content(1).toString());
	        }
	       
	        sresult.add(map);
	    }
	   
	    return  gson.toJson(sresult);
	}
	
	@GetMapping("/all-hotels")
	public static String allHotels() {
		Bucket bucket = openBucket(TRAVEL_SAMPLE);
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);	
		N1qlQueryResult result = bucket.query(N1qlQuery.simple(Q_HOTELS));
        cluster.disconnect();
		return gson.toJson(result.allRows().toString());
	}
	
	@GetMapping("/search-hotel-by-location")
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
	    SearchQuery query = new SearchQuery("airport", fts).limit(100);
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
	
	@GetMapping("/search-breweries-by-location")
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
