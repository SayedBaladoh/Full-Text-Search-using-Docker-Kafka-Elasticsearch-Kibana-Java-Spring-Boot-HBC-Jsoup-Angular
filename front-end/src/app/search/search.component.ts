import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

import { SearchService } from '../search/search.service';
import { pipe } from 'rxjs'
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  results: any[] = [];
  queryField: FormControl = new FormControl();
  searchAt: string;
  constructor(private _apiService: SearchService) {
    this.searchAt = "tweets";
  }

  ngOnInit() {

    this.queryField.valueChanges.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap((query) => (this.searchAt == 'tweets') ? this._apiService.searchTweets(query) : (this.searchAt == 'web') ? this._apiService.searchArticles(query) : this._apiService.search(query)
      )).subscribe((result: any) => { this.results = result });
  }

  onItemChange(item) {
    console.log("item: " + item)
    this.searchAt = item;
    let query = this.queryField.value;
    if ((this.searchAt == 'tweets'))
      this._apiService.searchTweets(query)
        .subscribe((result: any) => { this.results = result });
    else if ((this.searchAt == 'web'))
      this._apiService.searchArticles(query)
        .subscribe((result: any) => { this.results = result });
    else if ((this.searchAt == 'all'))
      this._apiService.search(query)
        .subscribe((result: any) => { this.results = result });
  }

  /** Get next page of search results */
  getResultsPage(pageNo) {
    let query = this.queryField.value;
    if ((this.searchAt == 'tweets'))
      this._apiService.searchTweetsPage(query, pageNo)
        .subscribe((result: any) => { this.results = result });
    else if ((this.searchAt == 'web'))
      this._apiService.searchArticlesPage(query, pageNo)
        .subscribe((result: any) => { this.results = result });
    else if ((this.searchAt == 'all'))
      this._apiService.searchPage(query, pageNo)
        .subscribe((result: any) => { this.results = result });
  }


}
