import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  root: string = 'http://localhost:8082';
  baseSearchUrl: string = this.root + '/consumer/api/search?q=';
  baseTweetsUrl: string = this.root + '/consumer/api/tweets?q=';
  baseArticlesUrl: string = this.root + '/consumer/api/articles?q=';
  corsHeaders: HttpHeaders;

  constructor(private http: HttpClient) {
    this.corsHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': this.root
    });
  }

  search(queryString: string) {
    let _URL = this.baseSearchUrl + queryString;
    return this.http.get(_URL, { headers: this.corsHeaders });
  }

  searchPage(queryString: string, pageNo: number) {
    let _URL = this.baseSearchUrl + queryString + '&page=' + pageNo;;
    return this.http.get(_URL, { headers: this.corsHeaders });
  }

  searchTweets(queryString: string) {
    let _URL = this.baseTweetsUrl + queryString;
    return this.http.get(_URL, { headers: this.corsHeaders });
  }

  searchTweetsPage(queryString: string, pageNo: number) {
    let _URL = this.baseTweetsUrl + queryString + '&page=' + pageNo;
    return this.http.get(_URL, { headers: this.corsHeaders });
  }

  searchArticles(queryString: string) {
    let _URL = this.baseArticlesUrl + queryString;
    return this.http.get(_URL, { headers: this.corsHeaders });
  }

  searchArticlesPage(queryString: string, pageNo: number) {
    let _URL = this.baseArticlesUrl + queryString + '&page=' + pageNo;;
    return this.http.get(_URL, { headers: this.corsHeaders });
  }

}
