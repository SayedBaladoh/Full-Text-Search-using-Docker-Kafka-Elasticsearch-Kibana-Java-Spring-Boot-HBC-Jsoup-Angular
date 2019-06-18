import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SettingService {

  rootProducer: string = 'http://localhost:8081';
  baseTwitterUrl: string = this.rootProducer + '/producer/api/stream/twitter?q=';
  baseCrawlerUrl: string = this.rootProducer + '/producer/api/stream/crawler?q=';

  rootConsumer: string = 'http://localhost:8082';
  baseTweetsUrl: string = this.rootConsumer + '/consumer/api/tweets';
  baseArticlesUrl: string = this.rootConsumer + '/consumer/api/articles';

  corsProducerHeaders: HttpHeaders;
  corsConsumerHeaders: HttpHeaders;

  constructor(private http: HttpClient) {
    this.corsProducerHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': this.rootProducer
    });

    this.corsConsumerHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': this.rootConsumer
    });
  }

  lanchTweetsStreamer(query: string, count: number) {
    // console.log("count: " + count);
    let _URL = this.baseTwitterUrl + query + (count ? '&count=' + count : '');
    // console.log("_URL: " + _URL);
    return this.http.get(_URL, { headers: this.corsProducerHeaders });
  }

  lanchCrawlerStreamer(query: string, url: string, count: number, maxPagesToSearch: number) {
    // console.log("count: " + count);
    let _URL = this.baseCrawlerUrl + query + '&url=' + url + (count ? '&count=' + count : '') + (maxPagesToSearch ? '&maxPagesToSearch=' + maxPagesToSearch : '');
    // console.log("_URL: " + _URL);
    return this.http.get(_URL, { headers: this.corsProducerHeaders });
  }

  deleteAllTweets() {
    let _URL = this.baseTweetsUrl + '/all';
    // console.log('deleteTweets'+ _URL)
    return this.http.delete(_URL, { headers: this.corsConsumerHeaders });

  }

  deleteAllArticles() {
    let _URL = this.baseArticlesUrl + '/all';
    // console.log('deleteAllArticles'+ _URL)
    return this.http.delete(_URL, { headers: this.corsConsumerHeaders });

  }
}
