import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SettingService } from '../setting/setting.service';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.css']
})
export class SettingComponent implements OnInit {

  twitterForm: FormGroup;
  crawlerForm: FormGroup;
  constructor(private _settingService: SettingService) { }

  ngOnInit() {
    this.twitterForm = new FormGroup({
      q: new FormControl('', Validators.required),
      count: new FormControl('')
    });

    this.crawlerForm = new FormGroup({
      url: new FormControl('http://', Validators.required),
      q: new FormControl('', Validators.required),
      count: new FormControl(''),
      maxPagesToSearch: new FormControl('')
    });

  }
  isTweeterDone = true;
  onSubmitTwitterForm(form: FormGroup) {
    // console.log('Valid?', form.valid); // true or false
    // console.log('query', form.value.q);
    // console.log('Count', form.value.count);
    if (form.valid) {
      this.isTweeterDone = false;
      this._settingService.lanchTweetsStreamer(form.value.q, form.value.count)
        .subscribe((result: any) => { this.isTweeterDone = true });
    }
  }

  isCrawlerDone = true;
  onSubmitCrawlerForm(form: FormGroup) {
    // console.log('Valid?', form.valid); // true or false
    // console.log('URL', form.value.url);
    // console.log('Query', form.value.q);
    // console.log('Count', form.value.count);
    // console.log('maxPagesToSearch', form.value.maxPagesToSearch);
    if (form.valid) {
      this.isCrawlerDone = false;
      this._settingService.lanchCrawlerStreamer(form.value.q, form.value.url, form.value.count, form.value.maxPagesToSearch)
        .subscribe((result: any) => { this.isCrawlerDone = true });
    }
  }

  isDeleteingTweets = false;
  deleteTweets() {
    this.isDeleteingTweets = true;
    this._settingService.deleteAllTweets()
      .subscribe(() => {
        this.isDeleteingTweets = false;
      });
  }

  isDeleteingArticles = false;
  deleteCrawler() {
    this.isDeleteingArticles = true;
    this._settingService.deleteAllArticles()
      .subscribe(() => {
        this.isDeleteingArticles = false;
      });
  }

}
