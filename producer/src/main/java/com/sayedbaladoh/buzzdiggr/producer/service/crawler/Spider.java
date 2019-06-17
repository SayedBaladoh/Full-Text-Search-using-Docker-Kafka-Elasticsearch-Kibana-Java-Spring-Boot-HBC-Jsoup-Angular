package com.sayedbaladoh.buzzdiggr.producer.service.crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sayedbaladoh.buzzdiggr.producer.model.Article;
import com.sayedbaladoh.buzzdiggr.producer.service.kafka.Sender;


/**
 * Simple web crawler
 * 
 * @author SayedBaladoh
 * 
 * Adapted from:
 * @see How to make a simple web crawler in Java -
 *      http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
 * @see Simple web crawler -
 *      https://www.programcreek.com/java-api-examples/?code=PacktPublishing/Machine-Learning-End-to-Endguide-for-Java-developers/Machine-Learning-End-to-Endguide-for-Java-developers-master/Module%201/JavaforDataScience_Code/chapter02/SimpleWebCrawler.java
 *      
 */
@Service
public class Spider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Spider.class);
	
	@Autowired
	private Sender sender;
	
	@Value("${kafka.topic.json.article}")
	private String articleTopicName;

	@Value("${crawler.maxPagesToSearch}")
	private int maxPagesToSearch;
	@Value("${crawler.pagesLimit}")
	private int pagesLimit;
	
	private String startingURL;
	private Set<String> visitedPages;
	private List<String> pageList;
	// Use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	/**
	 * Main launching point for the Spider's functionality with default maxPagesToSearch and pagesLimit.
	 * 
	 * @param url
	 *            - The starting point of the spider
	 * @param searchTopic
	 *            - The word or string that you are searching for
	 */
	public void crawl(String url, String topic) {
		
		visitedPages = new HashSet<String>();
		pageList = new ArrayList<String>();
		
		this.startingURL = url;
		this.search(url, topic);
		
		LOGGER.info("**Crawling Done** Visited:'{}' web page(s), Found:'{}' web page(s).", this.visitedPages.size(), this.pageList.size());
	}
	
	/**
	 * Overload the main launching point for the Spider's functionality with default maxPagesToSearch.
	 * 
	 * @param url
	 *            - The starting point of the spider
	 * @param searchTopic
	 *            - The word or string that you are searching for
	 * @param pagesLimit
	 *            - The limit for retrieving pages
	 */
	public void crawl(String url, String topic, int pagesLimit) {
		
		visitedPages = new HashSet<String>();
		pageList = new ArrayList<String>();
		
		this.startingURL = url;
		if (pagesLimit > 0)
			this.pagesLimit = pagesLimit;

		this.search(url, topic);
		
		LOGGER.info("**Crawling Done** Visited:'{}' web page(s), Found:'{}' web page(s).", this.visitedPages.size(), this.pageList.size());
	}
	
	/**
	 * Overload the launching point for the Spider's functionality with default pagesLimit.
	 * 
	 * @param url
	 *            - The starting point of the spider
	 * @param searchTopic
	 *            - The word or string that you are searching for
	 * @param maxPagesToSearch
	 *            - The maximum pages to search
	 */
	public void crawlByMax(String url, String topic, int maxPagesToSearch) {
		
		visitedPages = new HashSet<String>();
		pageList = new ArrayList<String>();
		
		this.startingURL = url;
		if (maxPagesToSearch > 0)
			this.maxPagesToSearch = maxPagesToSearch;

		this.search(url, topic);
		
		LOGGER.info("**Crawling Done** Visited:'{}' web page(s), Found:'{}' web page(s).", this.visitedPages.size(), this.pageList.size());
	}

	/**
	 * Overload the main launching point for the Spider's functionality.
	 * 
	 * @param url
	 *            - The starting point of the spider
	 * @param searchTopic
	 *            - The word or string that you are searching for
	 * @param pagesLimit
	 *            - The limit for retrieving pages
	 * @param maxPagesToSearch
	 *            - The maxmum pages to search
	 */
	public void crawl(String url, String topic, int pagesLimit, int maxPagesToSearch) {
		
		visitedPages = new HashSet<String>();
		pageList = new ArrayList<String>();
		
		this.startingURL = url;
		if (pagesLimit > 0)
			this.pagesLimit = pagesLimit;
		if (maxPagesToSearch > 0)
			this.maxPagesToSearch = maxPagesToSearch;

		this.search(url, topic);

		LOGGER.info("**Crawling Done** Visited:'{}' web page(s), Found:'{}' web page(s).", this.visitedPages.size(), this.pageList.size());
	}

	public void search(String url, String topic) {
		
		this.visitedPages.add(url);
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			// 200 is the HTTP OK status code indicating that everything
			// is great.
			// if (connection.response().statusCode() == 200) {
			// System.out.println("\n**Visiting** Received web page at " + url);
			// }
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Failure** Retrieved something other than HTML");
				return;
			}
			
			String text = htmlDocument.body().text();
			if (text.toLowerCase().contains(topic.toLowerCase())) {
				String title = htmlDocument.title();

				LOGGER.info("**Success search** Topic:'{}' is existed at:'{}' with title:'{}'.", topic, url, title);
				this.pageList.add(url);

				Article article = new Article(url, title, text);
				LOGGER.info("Article: {} ", article);
				
				//Send article to Kafka
				sender.send(articleTopicName, article);
				
			}
			
			// Process page links
			Elements linksOnPage = htmlDocument.select("a[href]");
			for (Element link : linksOnPage) {
				String absUrl = link.absUrl("href");
				// Check URL is not already visited, search in the same domain, pages limit and max pages to search
				if (absUrl.contains(this.startingURL) && !this.visitedPages.contains(absUrl)
						&& this.pageList.size() < this.pagesLimit && this.visitedPages.size() < this.maxPagesToSearch) {
					search(absUrl, topic);
				}
			}

		} catch (Exception ex) {
			// We were not successful in our HTTP request
			LOGGER.error(url, " must supply a valid URL");
		}
	}

//	/**
//	 * Test Case.
//	 * 
//	 * @param args
//	 *            - not used
//	 */
//	public static void main(String[] args) {
//		Spider spider = new Spider();
////		spider.crawl("https://www.shorouknews.com/", "Mohammad", 15);
//		spider.crawl("https://www.shorouknews.com/", "محمد", 15, 50);
//	}

}
